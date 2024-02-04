import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;
        final int puerto = 5000;
        final int puerto1 = 5002;
        final int puerto2 = 8001;

        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            while (true) {
                Socket sc = servidor.accept(); // Se queda a la espera
                System.out.println("Cliente conectado");
                DataInputStream in = new DataInputStream(sc.getInputStream()); // Crear puente del cliente al servidor
                DataOutputStream out = new DataOutputStream(sc.getOutputStream()); // Crear puente del servidor al cliente// Espera a que el cliente envíe algo


                int longitudArreglo = in.readInt();
                int[] arregloNumeros = new int[longitudArreglo];

                for (int i = 0; i < longitudArreglo; i++) {
                    arregloNumeros[i] = in.readInt();
                }

                // Dividir el arreglo en dos mitades
                int longitud = arregloNumeros.length;
                int mitad = longitud / 2;
                int[] primeraMitad = new int[mitad];
                int[] segundaMitad = new int[longitud - mitad];

                System.arraycopy(arregloNumeros, 0, primeraMitad, 0, mitad);
                System.arraycopy(arregloNumeros, mitad, segundaMitad, 0, longitud - mitad);

                int valor1 = enviarYRecibirMensaje(puerto1,primeraMitad);;
                int valor2=enviarYRecibirMensaje(puerto2,segundaMitad);;
                int suma = valor1 + valor2;
                out.writeInt(suma);
                sc.close();
                System.out.println("Cliente desconectado");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int enviarYRecibirMensaje(int puerto, int[] mensaje) {
        try (Socket socket = new Socket("127.0.0.1", puerto);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            // Enviar mensaje al servidor secundario
            out.writeInt(mensaje.length);
            for (int num : mensaje) {
                out.writeInt(num);
            }

            // Recibir y devolver la respuesta del servidor secundario
            return in.readInt();

        } catch (IOException e) {
            System.err.println("Error en la comunicación con el servidor en el puerto " + puerto);
            e.printStackTrace();
            return 0; // Otra acción según tu lógica de manejo de errores
        }
    }

}
