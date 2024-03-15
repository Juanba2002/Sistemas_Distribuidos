from concurrent import futures
import grpc
import array_pb2
import array_pb2_grpc

class ArrayService(array_pb2_grpc.ArrayServiceServicer):
    def SendArray(self, request, context):
        # Sumar los valores del arreglo
        suma = sum(request.values)
        print(suma)
        # Crear el mensaje de respuesta
        return array_pb2.Response(message=suma)

def serve():
    # Iniciar el servidor
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    array_pb2_grpc.add_ArrayServiceServicer_to_server(ArrayService(), server)
    server.add_insecure_port('[::]:5002')
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
