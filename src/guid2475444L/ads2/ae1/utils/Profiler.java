package guid2475444L.ads2.ae1.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


/**
 * Profiles the execution time for a {@link Runnable} {@link #target} by running it multiple times
 * (as specified by {@link #repeats}) and producing aggregate results (min & mean of execution
 * time).
 * <p><br>
 * A single invocation of {@link #timeIt(Runnable)} is referred to as a "repeat". Doing multiple
 * repeats allows for more statistically significant results, with less random noise in the
 * timings—caused by the fact that we're measuring wall-clock time, which is non-deterministic
 * (depends on system load at time of execution) and platform-dependant.
 */
public class Profiler {

    /**
     * Measure the execution time of a single invocation of a {@link Runnable}
     * @param target {@link Runnable} whose execution is to be timed
     * @return duration of execution
     */
    public static Duration timeIt(Runnable target) {
        Instant start = Instant.now();
        target.run();
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    /**
     * Encapsulates the results of {@link Profiler#profile()}
     */
    public static class Results {
        /** Immutable list of individual times for each repeat run */
        public final List<Duration> timings;
        /** Mean duration of runs */
        public final Duration mean;
        /** Shortest run */
        public final Duration min;

        /**
         * Synthesize aggregate profiling results from a list of individual timings
         * @param timings timings for individual runs of {@link Profiler#target}
         */
        public Results(List<Duration> timings) {
            this.timings = Collections.unmodifiableList(timings);
            final int n = timings.size();

            this.mean = timings.parallelStream().reduce(Duration.ZERO, Duration::plus).dividedBy(n);
            this.min = timings.stream().min(Duration::compareTo).orElse(Duration.ZERO);
        }

        @Override
        public String toString() {
            return String.format("min: %7dms, mean: %7dms", min.toMillis(), mean.toMillis());
        }
    }

    private Runnable target;
    private int repeats;

    /**
     * Set up a profiler
     * @param target  {@link Runnable} instance to be profiled
     * @param repeats number of times to run {@link #timeIt(Runnable)} to produce the aggregate
     *                results
     */
    public Profiler(Runnable target, int repeats) {
        this.target = target;
        this.repeats = repeats;
    }

    /**
     * Profile sequentially. Equivalent to {@code profile(Executors.newSingleThreadExecutor())}
     * @see #profile(ExecutorService)
     */
    public Results profile() {
        return profile(Executors.newSingleThreadExecutor());
    }

    /**
     * Profile the {@link #target} running each repeat as a task through a given {@link
     * ExecutorService}. Allows for parallelization.
     * @param exc executor service to run timing repeats against
     * @throws RuntimeException if any of the profiling tasks was interrupted or didn't complete
     *                          successfully
     */
    public Results profile(ExecutorService exc) {
        List<Callable<Duration>> tasks = new ArrayList<>(this.repeats);
        for (int i = 0; i < this.repeats; i++) tasks.add(() -> timeIt(target));
        try {
            List<Future<Duration>> futures = exc.invokeAll(tasks);
            List<Duration> timings = new ArrayList<>(futures.size());
            for (Future<Duration> future : futures) timings.add(future.get());
            return new Results(timings);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            exc.shutdown();
        }
    }

}
