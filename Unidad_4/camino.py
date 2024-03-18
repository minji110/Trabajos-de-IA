import csv
import heapq

# función para cargar datos desde un archivo CSV y devolver una lista de tuplas
def cargar_csv(archivo):
    datos = []
    with open(archivo, newline='') as csvfile:
        reader = csv.reader(csvfile)
        for i, row in enumerate(reader):
            if i == 0:  # Saltar la primera fila que contiene encabezados
                continue
            if len(row) == 3:  # Si hay tres columnas, asumimos que es el primer tipo de archivo
                datos.append((row[0], row[1], int(row[2])))
            elif len(row) == 2:  # Si hay dos columnas, asumimos que es el segundo tipo de archivo
                try:
                    datos.append((row[0], int(row[1])))
                except ValueError:
                    print(f"Error: valor no válido en la fila {i+1}: {row}")
    return datos

# Cargar datos del primer archivo CSV
archivo1 = "C:/Users/airam/Downloads/Línea recta a Bucharest.csv"
datos_ciudades = cargar_csv(archivo1)

# Cargar datos del segundo archivo CSV
archivo2 = "C:/Users/airam/Downloads/distancias entre ciudades.csv"
datos_distancias = cargar_csv(archivo2)

def obtener_distancia_recta_a_bucarest(ciudad):
    for dato in datos_ciudades:
        if dato[0] == ciudad:
            return dato[1]  # Acceder al segundo elemento de la tupla
    return None

def obtener_distancia_entre_ciudades(ciudad1, ciudad2):
    for dato in datos_distancias:
        if (dato[0] == ciudad1 and dato[1] == ciudad2) or (dato[0] == ciudad2 and dato[1] == ciudad1):
            return dato[2]  # La tercera columna contiene la distancia entre ciudades
    return None

def obtener_ciudades_conectadas(ciudad):
    ciudades_conectadas = []
    for dato in datos_distancias:
        if dato[0] == ciudad:
            ciudades_conectadas.append(dato[1])
        elif dato[1] == ciudad:
            ciudades_conectadas.append(dato[0])
    return ciudades_conectadas

def star(ciudad_inicio, ciudad_meta):
    # Estructuras de datos para almacenar nodos abiertos y cerrados
    nodos_abiertos = []
    nodos_cerrados = {}
    
    # Inicializar el nodo de inicio
    g_score = {ciudad_inicio: 0}
    h_score = {ciudad_inicio: obtener_distancia_recta_a_bucarest(ciudad_inicio)}
    f_score = {ciudad_inicio: h_score[ciudad_inicio]}
    heapq.heappush(nodos_abiertos, (f_score[ciudad_inicio], ciudad_inicio))
    
    while nodos_abiertos:
        _, ciudad_actual = heapq.heappop(nodos_abiertos)
        print("Explorando ciudad actual:", ciudad_actual)
        
        if ciudad_actual == ciudad_meta:
            # Reconstruir el camino óptimo
            camino_optimo = [ciudad_actual]
            while ciudad_actual in nodos_cerrados:
                ciudad_actual = nodos_cerrados[ciudad_actual]
                camino_optimo.append(ciudad_actual)
            return camino_optimo[::-1]  # Devolver el camino en orden correcto
        
        for ciudad_vecina in obtener_ciudades_conectadas(ciudad_actual):
            nuevo_g_score = g_score[ciudad_actual] + obtener_distancia_entre_ciudades(ciudad_actual, ciudad_vecina)
            print("Explorando vecino:", ciudad_vecina)
            
            if ciudad_vecina in g_score and nuevo_g_score >= g_score[ciudad_vecina]:
                print("No es un camino mejor")
                continue  # No es un camino mejor
                
            # Se ha encontrado un camino mejor
            nodos_cerrados[ciudad_vecina] = ciudad_actual
            g_score[ciudad_vecina] = nuevo_g_score
            h_score[ciudad_vecina] = obtener_distancia_recta_a_bucarest(ciudad_vecina)
            f_score[ciudad_vecina] = g_score[ciudad_vecina] + h_score[ciudad_vecina]
            heapq.heappush(nodos_abiertos, (f_score[ciudad_vecina], ciudad_vecina))
            print("Vecino añadido a nodos abiertos:", ciudad_vecina)
    
    # Si no se puede encontrar un camino, devolver None
    return None

# Ejemplo de uso
ciudad_inicio = "Arad"
ciudad_meta = "Bucharest"
ruta_optima = star(ciudad_inicio, ciudad_meta)
print("\n"+"Ruta óptima desde", ciudad_inicio, "a", ciudad_meta, ":", ruta_optima)