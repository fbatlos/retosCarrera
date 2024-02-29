import kotlin.random.Random


class Carrera(var nombreCarrera:String, val distanciaTotal:Float, var participantes:List<Vehiculo>){
    var estadoCarrera:Boolean = false
    var historialAcciones:MutableMap<String,MutableList<String>> = mutableMapOf()
    val posiciones = mutableMapOf<Int, String>()
    var contadorDeRepostado = mutableMapOf<String,Int>()
    var vecesFiligranas = 0


    fun iniciarCarrera(){
        estadoCarrera = true
        do {
            participantes.forEach { avanzarVehiculo(it) }
        }while (determinarGanador() == false)
        actualizarPosiciones()
        obtenerResultado()
    }

    fun avanzarVehiculo(vehiculo: Vehiculo){
        var distancia = Random.nextInt(10,250).toFloat()
        var contadorDeTramos = (distancia/20).toInt()
        do {
            realizarFiligrana(vehiculo)
            when {
                contadorDeTramos != 0 -> {
                    val paraLlegar = vehiculo.realizaViaje(20f)

                    distancia -= if ( paraLlegar == 0f) {
                        20f
                    }else{
                        (20f-paraLlegar)
                    }
                }
                contadorDeTramos == 0 -> {
                    distancia = vehiculo.realizaViaje(distancia)
                }
            }
            registarAccion(vehiculo,"${vehiculo.nombre} a recorrido ${vehiculo.kilometrosActuales.redondear(2)} y le quedan ${distancia.redondear(2)}")

            if (vehiculo.combustibleActual == 0f){
                repostarVehiculo(vehiculo)
            }

            contadorDeTramos--

        }while (contadorDeTramos != -1)
    }

    fun registarAccion(corredor:Vehiculo,accion:String){
        historialAcciones.computeIfAbsent(corredor.nombre){ mutableListOf()}.add(accion)
    }

    fun repostarVehiculo(vehiculo: Vehiculo){

        contadorDeRepostado.compute(vehiculo.nombre){ _,paradasARepostar-> (paradasARepostar?:0) + 1 }
        vehiculo.repostar()
        registarAccion(vehiculo, ("${vehiculo.nombre} a repostado."))
    }

    fun realizarFiligrana(vehiculo: Vehiculo){
        val numeroRandom = Random.nextInt(1,3)
        when(numeroRandom){
            1 ->{
                when(vehiculo){
                    is Automovil -> vehiculo.realizaDerrape()
                    is Motocicleta -> vehiculo.realizarCaballito()
                }
                vecesFiligranas++
                registarAccion(vehiculo,("${vehiculo.nombre} a realizado una filigrana."))
            }

            2 -> {
                if (vehiculo.combustibleActual>2f){
                    when(vehiculo){
                        is Automovil -> {
                            vehiculo.realizaDerrape()
                            vehiculo.realizaDerrape()
                        }
                        is Motocicleta -> {
                            vehiculo.realizarCaballito()
                            vehiculo.realizarCaballito()
                        }
                    }
                    vecesFiligranas+=2
                    registarAccion(vehiculo, ("${vehiculo.nombre} a realizado dos filigrana."))
                }else{
                    when(vehiculo){
                        is Automovil -> vehiculo.realizaDerrape()
                        is Motocicleta -> vehiculo.realizarCaballito()
                    }
                    vecesFiligranas++
                    registarAccion(vehiculo,("${vehiculo.nombre} a realizado una filigrana."))
                }
            }
        }
    }
    //Actualiza las posiciones
    fun actualizarPosiciones(){
        //Seleccionamos el parametro para comparar
        val comparador = compareBy<Vehiculo> {it.kilometrosActuales}
        //Ordenamos la lista segun el parametro sekleccionado
        participantes = participantes.sortedWith( comparador)
        var posicion = 1
        //recorremos la lista ordenada segun los kilometros actuales de vehiculo y le asignamos una posicion.
        for (vehiculo in participantes){
            posiciones.put(posicion,vehiculo.nombre)
            ++posicion
        }

    }

    fun determinarGanador():Boolean{
        //Seleccionamos el parametro para comparar
        val comparador = compareBy<Vehiculo> {it.kilometrosActuales}
        //Ordenamos la lista segun el parametro sekleccionado
        val listaOrdenada = participantes.sortedWith( comparador)
        if (listaOrdenada[5].kilometrosActuales >= 1000f){
            return true
        }
        else{return false}
    }

    fun obtenerResultado(){
        var posicion = 1
        var lugarEnPosiciones = 6
        var numeroDelParticipante = 5
        val resultados: MutableList<ResultadoCarrera> = mutableListOf()

        while (posicion != 7 ) {
            val resultado = ResultadoCarrera(
                participantes[numeroDelParticipante],
                posicion,
                participantes[numeroDelParticipante].kilometrosActuales.redondear(2),
                contadorDeRepostado.get(
                    posiciones.get(lugarEnPosiciones)
                ),
                historialAcciones.get(posiciones.get(lugarEnPosiciones))
            )
            resultados.add(resultado)
            ++posicion
            --lugarEnPosiciones
            --numeroDelParticipante
        }

        mostrarResultado(resultados)
    }

    fun mostrarResultado(resultados:MutableList<ResultadoCarrera>){
        val emojiCocheCarreras = "\uD83C\uDFCE"
        val emojiLineaMeta = "\uD83C\uDFC1"
        val posiciones = resultados.map { it.posicion }
        val nombres = resultados.map { it.vehiculo.nombre }
        println("                                                      $emojiLineaMeta                      ")
        println("                                                ${nombres[0]}$emojiCocheCarreras")
        println("                                   ${nombres[1]}$emojiCocheCarreras                  ")
        println("                              ${nombres[2]}$emojiCocheCarreras                            ")
        println("                     ${nombres[3]}$emojiCocheCarreras                                      ")
        println("        ${nombres[4]}$emojiCocheCarreras ")
        println(" ${nombres[5]}$emojiCocheCarreras ")

        val cantidadPuntos = 5
        val tiempoEsperaMs = 1000L
        for (i in 1..cantidadPuntos) {
            print(".")
            Thread.sleep(tiempoEsperaMs)
        }

        println("¡Tenemos ganador!")
        println("${posiciones[0]} ${nombres[0]} \uD83E\uDD47")
        println("${posiciones[1]} ${nombres[1]} \uD83E\uDD48")
        println("${posiciones[2]} ${nombres[2]} \uD83E\uDD49")

        println("Veamos el historial segun el orden de llegada.\n")
        val kmRecorridos = resultados.map { it.kilometraje }
        val paradasRepostaje = resultados.map { it.paradasRepostaje }
        val historialAcciones = resultados.map { it.historialAcciones }
        for (contador in 0..5){
            println("${posiciones[contador]}º ${nombres[contador]} con ${kmRecorridos[contador]}km , ${paradasRepostaje[contador]} veces a repostado y sus acciones han sido ${historialAcciones[contador]}")
        }
    }

 
}

data class ResultadoCarrera(
    val vehiculo: Vehiculo,
    val posicion: Int,
    val kilometraje: Float,
    val paradasRepostaje: Int?,
    val historialAcciones: MutableList<String>?
)