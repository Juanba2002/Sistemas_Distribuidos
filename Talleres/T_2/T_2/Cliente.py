import grpc
import array_pb2
import array_pb2_grpc

def run():
    # Conexión al servidor
    channel = grpc.insecure_channel('localhost:5000')
    stub = array_pb2_grpc.ArrayServiceStub(channel)

    # Crear el arreglo
    arreglo = array_pb2.Array(values=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13])

    # Enviar el arreglo al servidor y recibir la respuesta
    response = stub.SendArray(arreglo)

    print(response.message)

if __name__ == '__main__':
    run()
