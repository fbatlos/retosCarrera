class Motocicleta(
    nombre:String,
    marca:String,
    modelo:String,
    capacidadCombustible:Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    var cilindrada:Int
):Vehiculo(nombre,marca, modelo, capacidadCombustible,
    combustibleActual, kilometrosActuales
){

    init {
        require(cilindrada>125 && cilindrada<1000){"Cilindrada no permitida."}
    }

    override fun calcularAutonomia(): Float {
        return (combustibleActual* (KM_Litros_Motos+(cilindrada/1000))).redondear(2)
    }

    fun realizarCaballito():Float{
        when{
            cilindrada == 100 -> combustibleActual-=0.325F
            cilindrada >= 650 -> combustibleActual-=0.225F
            cilindrada <  650 -> combustibleActual-=0.125F
         }
        return combustibleActual
    }
    override fun realizaViaje(distancia: Float): Float {
            val DistanciaTotal = calcularAutonomia()
            if (DistanciaTotal > distancia) {
                combustibleActual -= (distancia / KM_Litros_Motos)
                kilometrosActuales += distancia
                return 0f
            }
            combustibleActual = 0F
            kilometrosActuales += (DistanciaTotal)
            return (distancia - DistanciaTotal).redondear(2)
    }

    override fun toString(): String {
        return "${super.toString()} , de cilindada : $cilindrada"
    }

    companion object{
        const val KM_Litros_Motos = 19
    }
}