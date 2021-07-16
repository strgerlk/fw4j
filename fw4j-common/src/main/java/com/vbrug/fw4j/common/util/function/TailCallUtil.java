package com.vbrug.fw4j.common.util.function;


public class TailCallUtil {

    public static <T> TailCall<T> call (final TailCall<T> nextCall) {
        return nextCall;
    }

    public static <T> TailCall<T> done (final T value) {
        return new TailCall<T>() {

            @Override
            public boolean isComplete() {
                return true;
            }

            @Override
            public T result() {
                return value;
            }

            @Override
            public TailCall<T> apply() {
                throw new Error("end of recursion");
            }

        };
    }

    public static void main(String[] args) {
        System.out.println(TailCallUtil.factorialTailRec(1, 10).invoke());
    }

    public static TailCall<Integer> factorialTailRec(final int factorial, final int number) {
        if (number == 1)
            return TailCallUtil.done(factorial);
        else
            return TailCallUtil.call(() -> factorialTailRec(factorial*number, number-1));
    }

}
