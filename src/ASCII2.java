
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ASCII2 {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;
        int opcion;
        Object imagepath;
        welcome();
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1 -> {
                    System.out.println("Selecciona archivo..");
                    generarArteASCII();
                }
                case 2 -> {
                    System.out.println("Saliendo del programa...");

                }
                default -> {
                    System.out.println("Opción no válida. Por favor, seleccione una opción entre 1 y 2.");
                }
            }
        } while (opcion != 2);
        teclado.close();
        System.exit(0);

    }

    static int setAncho(){
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce la anchura de la imagen seleccionada: ");
        int ancho = teclado.nextInt();
        return ancho;
    }

    static void generarArteASCII() {
        // Coloca aquí el código para generar el arte ASCII
        // Crear un nuevo diálogo de archivo
        FileDialog fileDialog = new FileDialog((Frame) null, "Selecciona imagen");
        fileDialog.setVisible(true);

        // Obtener la ruta de la imagen seleccionada por el usuario
        String imagePath = fileDialog.getDirectory() + fileDialog.getFile();
        System.out.println("\nGenerando imagen...");

        // Caracteres para la conversión
        char[] asciiChars = { '@', '#', '8', '&', 'o', ':', '*', '=', '+', '-', '^', '.', ' ', '`', ',', '"', '<', '>',
                '|', '(', ')', '[', ']', '{', '}', '/', '\\', '_', '?', '!', '~', ';', 'I', 'l', '!', 'i', ',', ':',
                ';', '-', '~', '"', '^',
                '\'', ',', '.', ' ', '¨', '¯', '·', '˜', 'º', '°', '˛', 'ˇ', 'ˆ', '˙', '˘', '˚', '˜', '˝', '¯', '¯',
                '¯', '´', '¸', '˛', 'ˇ', 'ˆ', '˙', '˚', '˜', '˝', '¯', '¯', '¯', '´', '¸', '˛', 'ˇ', 'ˆ', '˙', '˚', '˜',
                '˝', '¯', '¯', '¯', '´', '¸',
                '˛', 'ˇ', 'ˆ', '˙', '˚', '˜', '˝', '¯', '¯', '¯', '´', '¸', '˛', 'ˇ', 'ˆ', '˙', '˚', '˜', '˝', '¯', '¯',
                '¯', '´', '¸', '˛', 'ˇ', 'ˆ', '˙', '˚', '˜', '˝', '¯', '¯', '¯', '´', '¸', '˛', 'ˇ', 'ˆ', '˙', '˚', '˜',
                '˝', '¯', '¯', '¯', '´', '¸',
                '˛', 'ˇ', 'ˆ', '˙', '˚', '˜', '˝', '¯', '¯', '¯', '´', '¸', '˛', 'ˇ', 'ˆ', '˙', '˚', '˜', '˝', '¯', '¯',
                '¯' };

        String outputBaseName = NombraFichero();
        File outputFile = new File(outputBaseName);
        int ancho; //Poner el ancho de la imagen

        try {
            // Leer la imagen de entrada
            BufferedImage image = ImageIO.read(new File(imagePath));
            ancho = setAncho();
            // Redimensionar la imagen para que tenga el mismo aspecto que la salida
            BufferedImage resizedImage = resizeImage(image, ancho); // Redimensionar la imagen a 200 píxeles de ancho

            // Ruta del archivo de salida
            // Verificar si el archivo ya existe
            int counter = 1;
            while (outputFile.exists()) {
                // Generar un nuevo nombre de archivo con un sufijo numérico único
                outputBaseName = "ascii_art_" + counter + ".txt";
                outputFile = new File(outputBaseName);
                counter++;
            }
            // Guardar el arte ASCII en un archivo
            try (Writer writer = new BufferedWriter(new FileWriter(outputBaseName))) {
                convertToAsciiAndWrite(resizedImage, asciiChars, writer);
            }

            System.out.println("Arte ASCII guardado en " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(e);

        }

    }

    private static void convertToAsciiAndWrite(BufferedImage image, char[] asciiChars, Writer writer)
            throws IOException {
        try {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Obtener el color del píxel en la imagen
                    int color = image.getRGB(x, y);
                    // Calcular la escala de grises promedio
                    int gray = (color & 0xFF) / 3; // Usamos solo el componente azul
                    // Convertir el valor de escala de grises a un carácter ASCII
                    int index = (int) (gray * (asciiChars.length - 1) / 255.0);
                    // Escribir el carácter en el archivo
                    writer.write(asciiChars[index]);
                }
                writer.write("\n"); // Nueva línea después de cada fila de píxeles
            }
        } catch (IOException e) {
            // Manejar la excepción IOException
            System.err.println("Error: Ocurrió un problema al escribir en el archivo.");
            e.printStackTrace();
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int newWidth) {
        double aspectRatio = (double) originalImage.getHeight() / originalImage.getWidth();
        int newHeight = (int) (newWidth * aspectRatio);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    static String NombraFichero() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese el nombre del archnivo de salida (sin extension): ");
        String filename = teclado.nextLine();
        if (filename.trim().isEmpty()) {
            filename = "ascii_art";
        }
        return filename + ".txt";
    }

    public static int menu(Scanner teclado) {
        final String RESET = "\u001B[0m"; // Reset formatting
        final String BOLD = "\u001B[1m"; // Bold text
        final String CYAN = "\u001B[36m"; // Cyan text
        final String YELLOW = "\u001B[33m"; // Yellow text
        System.out.println();
        System.out.println(BOLD + CYAN + "=============================================" + RESET);
        System.out.println(BOLD + YELLOW + "             MENU PRINCIPAL                " + RESET);
        System.out.println(BOLD + CYAN + "=============================================" + RESET);
        System.out.println("\n" + BOLD + "  1. " + YELLOW + "Generar Nueva Imagen" + RESET);
        System.out.println(BOLD+"  2. " + YELLOW + "Salir" + RESET);
        System.out.print("\n" + BOLD + CYAN + "Elija una opción: " + RESET);

        return teclado.nextInt();
    }

    static void welcome() {
        final String RESET = "\u001B[0m"; // Reset formatting
        final String BOLD = "\u001B[1m"; // Bold text
        final String UNDERLINE = "\u001B[4m"; // Underline text
        final String RED = "\u001B[31m"; // Red text
        final String YELLOW = "\u001B[33m"; // Yellow text
        final String GREEN = "\u001B[32m"; // Green text
        final String CYAN = "\u001B[36m"; // Cyan text
        System.out.println("\n"
                +BOLD + RED + "===================================================================================================================\n"
                +BOLD + RED + " ▄▄▄        ██████ ▄████▄   ██▓ ██▓     ▄████ ▓█████ ███▄    █▓█████  ██▀███  ▄▄▄     ▄▄▄█████▓ ▒█████   ██▀███  \n"
                + "▒████▄    ▒██    ▒▒██▀ ▀█  ▓██▒▓██▒    ██▒ ▀█▒▓█   ▀ ██ ▀█   █▓█   ▀ ▓██ ▒ ██▒████▄   ▓  ██▒ ▓▒▒██▒  ██▒▓██ ▒ ██▒\n"
                + "▒██  ▀█▄  ░ ▓██▄  ▒▓█    ▄ ▒██▒▒██▒   ▒██░▄▄▄░▒███  ▓██  ▀█ ██▒███   ▓██ ░▄█ ▒██  ▀█▄ ▒ ▓██░ ▒░▒██░  ██▒▓██ ░▄█ ▒\n"
                + "░██▄▄▄▄██   ▒   ██▒▓▓▄ ▄██▒░██░░██░   ░▓█  ██▓▒▓█  ▄▓██▒  ▐▌██▒▓█  ▄ ▒██▀▀█▄ ░██▄▄▄▄██░ ▓██▓ ░ ▒██   ██░▒██▀▀█▄  \n"
                + " ▓█   ▓██▒▒██████▒▒ ▓███▀ ░░██░░██░   ░▒▓███▀▒░▒████▒██░   ▓██░▒████▒░██▓ ▒██▒▓█   ▓██▒ ▒██▒ ░ ░ ████▓▒░░██▓ ▒██▒\n"
                + " ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░ ░▒ ▒  ░░▓  ░▓      ░▒   ▒ ░░ ▒░ ░ ▒░   ▒ ▒░░ ▒░ ░░ ▒▓ ░▒▓░▒▒   ▓▒█░ ▒ ░░   ░ ▒░▒░▒░ ░ ▒▓ ░▒▓░\n"
                + "  ▒   ▒▒ ░░ ░▒  ░ ░ ░  ▒    ▒ ░ ▒ ░     ░   ░  ░ ░  ░ ░░   ░ ▒░░ ░  ░  ░▒ ░ ▒░ ▒   ▒▒ ░   ░      ░ ▒ ▒░   ░▒ ░ ▒░\n"
                + "  ░   ▒   ░  ░  ░ ░         ▒ ░ ▒ ░   ░ ░   ░    ░     ░   ░ ░   ░     ░░   ░  ░   ▒    ░      ░ ░ ░ ▒    ░░   ░ \n"
                + "      ░  ░      ░ ░ ░       ░   ░           ░    ░  ░        ░   ░  ░   ░          ░  ░            ░ ░     ░     \n"
                + "===================================================================================================================\n"
                + "Genera tus imagenes a formato ASCII (Rescalado a 800 por defecto.)\n"
                + "===================================================================================================================");
    }

}
