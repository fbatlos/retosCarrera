class Automovil(
                nombre:String,
                marca:String,
                modelo:String,
                capacidadCombustible:Float,
                combustibleActual:Float,
                kilometrosActuales: Float,
                val esElectrico :Boolean,
                var condicionBritanica:Boolean = false
                ):Vehiculo(nombre,marca , modelo , capacidadCombustible , combustibleActual ,kilometrosActuales
){

    fun cambiarCondicionBritania(nuevaCondicion:Boolean){
        condicionBritanica = cambiarABritanica()
    }

    override fun calcularAutonomia(): Float {
        if (esElectrico){
            return (combustibleActual* KM_Litro_Hibrido).redondear(2)
        }
        return (combustibleActual* KM_Litros_GAS).redondear(2)
    }

    fun realizaDerrape():Float{
        if (esElectrico){
            combustibleActual -= 0.625f
        }else {
            combustibleActual -= 0.75f
        }
        return combustibleActual
    }


    override fun realizaViaje(distancia: Float): Float {
        if (esElectrico){
            val DistanciaTotal = calcularAutonomia()
            if (DistanciaTotal>distancia) {
                combustibleActual -=(distancia/ KM_Litro_Hibrido).redondear(2)
                kilometrosActuales +=distancia
                return 0f
            }
            combustibleActual = 0F
            kilometrosActuales += (distancia - DistanciaTotal).toInt()
            return (distancia - DistanciaTotal).redondear(2)
        }else {
            val DistanciaTotal = calcularAutonomia()
            if (DistanciaTotal > distancia) {
                combustibleActual -= (distancia / KM_Litros_GAS)
                kilometrosActuales += distancia
                return 0f
            }
            combustibleActual = 0F
            kilometrosActuales += (DistanciaTotal)
            return (distancia - DistanciaTotal).redondear(2)
        }
    }

    override fun toString(): String {
        return "${super.toString()}"
    }

    companion object{
        const val KM_Litro_Hibrido = 15f
        fun cambiarABritanica():Boolean {
            return true
        }
    }
}