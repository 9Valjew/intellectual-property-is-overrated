package mcheli.eval.eval.func;

public class VoidFunction implements Function
{
    @Override
    public long evalLong(final Object object, final String name, final Long[] args) throws Throwable {
        System.out.println(object + "." + name + "\u95a2\u6570\u304c\u547c\u3070\u308c\u305f(long)");
        for (int i = 0; i < args.length; ++i) {
            System.out.println("arg[" + i + "] " + args[i]);
        }
        return 0L;
    }
    
    @Override
    public double evalDouble(final Object object, final String name, final Double[] args) throws Throwable {
        System.out.println(object + "." + name + "\u95a2\u6570\u304c\u547c\u3070\u308c\u305f(double)");
        for (int i = 0; i < args.length; ++i) {
            System.out.println("arg[" + i + "] " + args[i]);
        }
        return 0.0;
    }
    
    @Override
    public Object evalObject(final Object object, final String name, final Object[] args) throws Throwable {
        System.out.println(object + "." + name + "\u95a2\u6570\u304c\u547c\u3070\u308c\u305f(Object)");
        for (int i = 0; i < args.length; ++i) {
            System.out.println("arg[" + i + "] " + args[i]);
        }
        return null;
    }
}
