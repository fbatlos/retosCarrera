import kotlin.math.pow
import kotlin.math.roundToInt

open class Vehiculo(open val nombre: String,open val marca: String, open val modelo: String, open val capacidadCombustible: Float, open var combustibleActual: Float, open var kilometrosActuales:Float){
    init {
        require(!comprobarNombre(this.nombre)){"El nombre está repetido."}
        require(capacidadCombustible>=combustibleActual && capacidadCombustible > 0){"No valido el volumen del combustible."}
        require(combustibleActual>0){"No valido el volumen del combustible."}
    }

    companion object{
        private val listadonombres:MutableSet<String> = mutableSetOf()
        private fun comprobarNombre(nombre: String) = !listadonombres.add(nombre)
    }
    var KM_Litros_GAS = 10f

    open fun calcularAutonomia():Float{
        return (combustibleActual* KM_Litros_GAS).redondear(2)
    }

    open fun realizaViaje(distancia:Float ):Float{
        val DistanciaTotal = combustibleActual* KM_Litros_GAS
        if (DistanciaTotal>distancia) {
            combustibleActual -=(distancia/ KM_Litros_GAS)
            kilometrosActuales +=distancia
            return 0f
        }
        combustibleActual = 0F
        kilometrosActuales += (DistanciaTotal)
        return (distancia - DistanciaTotal).redondear(2)
    }

    open fun repostar(cantida:Float = 0f):Float{
        if (capacidadCombustible<(combustibleActual+cantida) ||cantida<= 0f){
            combustibleActual = capacidadCombustible
            return (combustibleActual.redondear(2))
        }
        combustibleActual += cantida
        return cantida.redondear(2)
    }

    override fun toString(): String {
        return "$nombre tu $marca $modelo , con una autonomia ${calcularAutonomia()}"
    }

}

const val diez = 10.0
fun Float.redondear(posiciones:Int):Float{
    val coma = (diez.pow(posiciones)).toFloat()
    val numero = this
    val redondeo = (numero*coma).roundToInt()/coma
    return redondeo
}