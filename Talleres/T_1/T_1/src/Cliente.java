import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        final String Host="127.0.0.1";
        final String Host2="10.43.100.20";
        final int puerto=5000;
        DataInputStream in;
        DataOutputStream out;
        try {
            Socket sc=new Socket(Host,puerto);
            int[] arreglo = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13};
            in= new DataInputStream(sc.getInputStream());//Crear puente del servidor al cliente
            out=new DataOutputStream(sc.getOutputStream());//Crear puente del cliente al servidor

            out.writeInt(arreglo.length);
            for(int num:arreglo)
            {
                out.writeInt(num);
            }
            int mensaje=in.readInt();
            System.out.println(mensaje);
            sc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}