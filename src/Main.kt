fun main(){
    val listaparticipantes:List<Vehiculo> = listOf(
        Automovil("Aurora", "Seat", "Panda", 50f, 50f * 0.1f, 0f, true) , // Coche eléctrico con capacidad de 50 litros, inicia con el 10%
        Automovil("Boreal", "BMW", "M8", 80f, 80f * 0.1f, 0f, false), // SUV híbrido con capacidad de 80 litros, inicia con el 10%
        Motocicleta("Céfiro", "Derbi", "Motoreta", 15f, 15f * 0.1f, 0f, 500) , // Motocicleta de gran cilindrada con capacidad de 15 litros, inicia con el 10%
        Automovil("Dinamo", "Cintroen", "Sor", 70f, 70f * 0.1f, 0f, true), // Camioneta eléctrica con capacidad de 70 litros, inicia con el 10%
        Automovil("Eclipse", "Renault", "Espacio", 60f, 60f * 0.1f, 0f, false), // Coupé deportivo con capacidad de 60 litros, inicia con el 10%
        Motocicleta("Fénix", "Honda", "Vital", 20f, 20f * 0.1f, 0f, 250) // Motocicleta eléctrica con capacidad de 20 litros, inicia con el 10%
    )

    var frase = "   hola    jasa    poie      "
    frase = frase.normalizar()
    println(frase)

    val megaCarrera = Carrera("Gran Prix",1000f , listaparticipantes)

    megaCarrera.iniciarCarrera()

}

