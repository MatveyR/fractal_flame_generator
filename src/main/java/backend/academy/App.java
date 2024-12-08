package backend.academy;

import backend.academy.processors.GammaCorrectionProcessor;
import backend.academy.renderers.MultipleRenderer;
import backend.academy.transformations.DiamondTransformation;
import backend.academy.transformations.DiscTransformation;
import backend.academy.transformations.HeartTransformation;
import backend.academy.transformations.HyperbolTransformation;
import backend.academy.transformations.PolarTransformation;
import backend.academy.transformations.SinTransformation;
import backend.academy.transformations.SphereTransformation;
import backend.academy.transformations.SpiralTransformation;
import backend.academy.transformations.Variation;
import backend.academy.utils.ImageFormat;
import backend.academy.utils.ImageUtils;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int MAX_COLOR = 256;
    private final PrintStream out;

    public App(PrintStream out) {
        this.out = out;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        out.print("Введите ширину изображения: ");
        int width = scanner.nextInt();
        out.print("Введите высоту изображения: ");
        int height = scanner.nextInt();

        out.print("Введите симметрию: ");
        int symmetry = scanner.nextInt();
        out.print("Введите количество samples: ");
        int samples = scanner.nextInt();
        out.print("Введите количество итераций (iters): ");
        int iterations = scanner.nextInt();
        out.print("Введите количество потоков: ");
        int threads = scanner.nextInt();

        out.println("Выберите трансформации (введите через запятую):");
        out.println(
            "1 - Diamond, 2 - Disc, 3 - Heart, 4 - Hyperbol, 5 - Polar, 6 - Sin, 7 - Sphere, 8 - Spiral");
        scanner.nextLine();
        String[] transformationChoices = scanner.nextLine().split(",");

        FractalImage image = FractalImage.create(width, height);

        MultipleRenderer renderer = new MultipleRenderer();
        renderer.render(image, getVariations(transformationChoices), symmetry, samples, iterations, threads);

        ImageUtils utils = new ImageUtils();
        GammaCorrectionProcessor processor = new GammaCorrectionProcessor();
        processor.process(image);

        out.print("Введите путь для сохранения изображения (например, C:\\rnd.png): ");
        String outputPath = scanner.nextLine();
        utils.save(image, Path.of(outputPath), ImageFormat.PNG);

        out.println("Изображение успешно создано и сохранено.");
    }

    private List<Variation> getVariations(String[] choices) {
        Random random = new Random();
        List<Variation> vars = new ArrayList<>();

        for (String choice : choices) {
            switch (choice.trim()) {
                case "1":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new DiamondTransformation()));
                    break;
                case "2":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new DiscTransformation()));
                    break;
                case "3":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new HeartTransformation()));
                    break;
                case "4":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new HyperbolTransformation()));
                    break;
                case "5":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new PolarTransformation()));
                    break;
                case "6":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new SinTransformation()));
                    break;
                case "7":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new SphereTransformation()));
                    break;
                case "8":
                    vars.add(
                        new Variation(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR),
                            new SpiralTransformation()));
                    break;
                default:
                    out.println("Неизвестная трансформация: " + choice.trim());
            }
        }
        return vars;
    }
}
