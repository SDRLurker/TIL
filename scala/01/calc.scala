object calc extends App{
        def square(x: Double): Double = x*x
        def cubic(x: Double): Double = square(x)*x
        def square(x: Int): Int = x*x
        def cubic(x: Int): Int = square(x)*x
        println(square(2.2))
        println(cubic(3.3))
        println(square(2))
        println(cubic(3))
        println(calc.square(2.0)==4.0)
        println(calc.cubic(2.0)==8.0)
        println(calc.square(2)==4)
        println(calc.cubic(3)==27)
}
