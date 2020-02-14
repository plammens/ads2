package guid2475444L.ads2.ae1;

import static guid2475444L.ads2.ae1.utils.Utils.readIntList;
import static guid2475444L.ads2.ae1.utils.Utils.toIntArray;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import guid2475444L.ads2.ae1.sorters.CutoffQuickSort;
import guid2475444L.ads2.ae1.sorters.MedianOfThreeQuickSort;
import guid2475444L.ads2.ae1.sorters.QuickSort;
import guid2475444L.ads2.ae1.sorters.ThreeWayQuickSort;
import org.apache.commons.cli.*;


public class TimeSortingAlgorithms {

    public static final String CLI_SYNOPSIS =
            "TimeSortingAlgorithms [--help] <input-files...> [--algorithms <sorters...>]]";
    public static final Options CLI_OPTIONS = new Options();
    public static final Pattern SORTER_SPEC_PATTERN = Pattern.compile(
            "(?<class>\\w+)(?:\\((?<arg>\\d*)\\))?");
    public static final List<Sorter> DEFAULT_SORTERS = Arrays.asList(new Sorter[]{
            new QuickSort(),
            new CutoffQuickSort(3),
            new CutoffQuickSort(50),
            new CutoffQuickSort(200),
            new MedianOfThreeQuickSort(),
            new ThreeWayQuickSort(),
    });

    static {
        final String algorithmsDescription =
                "specify the sorting algorithms to time against input as space separated tokens "
                        + "following the syntax `<sorter-name>[(<constructor-arg>)]`, where "
                        + "<sorter-name> is the unqualified name of a class in guid2475444L.ads2"
                        + ".ae1.sorters and "
                        + "<constructor-arg> is the constructor argument, if applicable.";

        CLI_OPTIONS.addOption("h", "help", false, "show this help message and exit");
        CLI_OPTIONS.addOption(Option.builder("a").longOpt("algorithms")
                                    .hasArgs().argName("sorter...")
                                    .desc(algorithmsDescription).build());
    }

    /**
     * Time a sorting algorithm on an array. The sorting will be done as a copy so that {@code arr
     * is not mutated}.
     * @param sorter sorting algorithm to use
     * @param arr array to sort
     * @return time elapsed to execute {@code sorter.sorted(arr);}
     */
    public static Duration timeSorter(Sorter sorter, int[] arr) {
        Instant start = Instant.now();
        sorter.sorted(arr);
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    public static void main(String[] args) throws ParseException {
        TimeSortingAlgorithms config = parseCmdLine(args);
        config.runTimings();
    }

    /**
     * Parse a run configuration from a command-line invocation
     * @param args command-line arguments
     * @return run configuration
     * @throws ParseException if the command-line is malformed
     */
    private static TimeSortingAlgorithms parseCmdLine(String[] args) throws ParseException {
        CommandLine cmdLine = new DefaultParser().parse(CLI_OPTIONS, args);
        // if help flag is set, print help and exit early:
        if (cmdLine.hasOption("help")) {
            new HelpFormatter().printHelp(CLI_SYNOPSIS, CLI_OPTIONS);
            System.exit(0);
        }

        List<Sorter> sorters = parseSorters(cmdLine.getOptionValues("algorithms"));
        String[] inputFiles = cmdLine.getArgs();
        return new TimeSortingAlgorithms(sorters, inputFiles);
    }

    /**
     * Create a list of {@link Sorter} instances based on string specifications given at the command
     * line. Skips malformed specifications, printing error messages to stderr
     * @param sorterSpecs list of sorter instance specifications given at the command line
     * @return list of {@link Sorter} instances corresponding to the {@code sorterSpecs}
     */
    private static List<Sorter> parseSorters(String[] sorterSpecs) {
        if (sorterSpecs == null || sorterSpecs.length == 0)
            return DEFAULT_SORTERS;

        System.out.println("Collecting sorters...");
        List<Sorter> sorters = new ArrayList<>(sorterSpecs.length);
        for (String spec : sorterSpecs) {
            try {
                Sorter sorter = makeSorter(spec);
                sorters.add(sorter);
            } catch (ParseException | ClassNotFoundException e) {
                System.err.println(e);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Done.\n");

        return sorters;
    }

    /**
     * Construct an instance of a sorting algorithm (a class implementing {@link Sorter}) based on a
     * String specification.
     * @param spec string specification of the sorting algorithm. Must have the following
     *         syntax:
     *         <pre>{@code <sorter-name>[(<constructor-arg>)]}</pre>
     *         Where {@code <sorter-name>} must be the unqualified name of a class in {@link
     *         guid2475444L.ads2.ae1.sorters} that implements {@link Sorter}, and the {@code
     *         <constructor-arg>} must
     *         only be specified if applicable to that sorter class.
     *         <br><br>
     *         Examples:
     *         <ul>
     *             <li>{@code "InsertionSort"}</li>
     *             <li>{@code "CutoffQuickSort(50)"}</li>
     *             <li>{@code "QuickSort"}</li>
     *         </ul>
     * @return a new {@link Sorter} instance as specified in {@code spec}
     * @throws ParseException               if {@code spec} doesn't abide by the syntax given above
     * @throws ReflectiveOperationException if there was an error loading the class specified by
     *                                      {@code spec} or instantiating an appropriate {@link
     *                                      Sorter} object
     */
    private static Sorter makeSorter(String spec) throws ParseException,
                                                         ReflectiveOperationException {
        Matcher matcher = SORTER_SPEC_PATTERN.matcher(spec);
        if (!matcher.matches())
            throw new ParseException("sorter specification not recognized: " + spec);

        String className = "guid2475444L.ads2.ae1.sorters." + matcher.group("class");
        String arg = matcher.group("arg");

        @SuppressWarnings("unchecked")
        Class<Sorter> sorterClass = (Class<Sorter>) Class.forName(className);
        return (arg == null) ? sorterClass.getConstructor().newInstance()
                             : sorterClass.getConstructor(int.class)
                                          .newInstance(Integer.parseInt(arg));
    }

    private List<Sorter> sorters;
    private String[] inputFiles;

    public TimeSortingAlgorithms(List<Sorter> sorters, String[] inputFiles) {
        this.sorters = sorters;
        this.inputFiles = inputFiles;
    }

    private void runTimings() {
        System.out.println("Timing the algorithm(s):");
        for (Sorter sorter : sorters)
            System.out.println("\t" + sorter);
        System.out.println("against the following input:");
        for (String path : inputFiles)
            System.out.println("\t" + path);

        for (String path : inputFiles) {
            System.out.println();
            try {
                FileInputStream file = new FileInputStream(path);
                System.out.println("--------------------------------------------------");
                System.out.println("INPUT: " + path);

                int[] arr = toIntArray(readIntList(file));
                file.close();
                System.out.println();
                for (Sorter sorter : sorters) {
                    Duration t = timeSorter(sorter, arr);
                    System.out.printf("%s: %ds %09dns\n", sorter, t.getSeconds(), t.getNano());
                }

                System.out.println("--------------------------------------------------");
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }

}
