from concurrent import futures
import grpc
import array_pb2
import array_pb2_grpc

class ArrayService(array_pb2_grpc.ArrayServiceServicer):
    def SendArray(self, request, context):
        # Dividir el arreglo en dos mitades
        longitud = len(request.values)
        mitad = longitud // 2
        primeraMitad = request.values[:mitad]
        segundaMitad = request.values[mitad:]

        # Enviar las mitades a los servidores secundarios y recibir las respuestas
        valor1 = enviarYRecibirMensaje('localhost:5002', primeraMitad)
        valor2 = enviarYRecibirMensaje('localhost:8001', segundaMitad)

        # Sumar los valores y devolver la respuesta
        suma = valor1 + valor2
        return array_pb2.Response(message=suma)

def serve():
    # Iniciar el servidor
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    array_pb2_grpc.add_ArrayServiceServicer_to_server(ArrayService(), server)
    server.add_insecure_port('[::]:5000')
    server.start()
    server.wait_for_termination()

def enviarYRecibirMensaje(host, mensaje):
    # Conexión al servidor secundario
    channel = grpc.insecure_channel(host)
    stub = array_pb2_grpc.ArrayServiceStub(channel)

    # Crear el arreglo
    arreglo = array_pb2.Array(values=mensaje)

    # Enviar el arreglo al servidor secundario y recibir la respuesta
    response = stub.SendArray(arreglo)

    return response.message

if __name__ == '__main__':
    serve()
