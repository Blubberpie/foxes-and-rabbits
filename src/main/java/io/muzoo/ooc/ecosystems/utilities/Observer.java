package io.muzoo.ooc.ecosystems.utilities;

public interface Observer<S extends Observable<S, O, A, B>,
                          O extends Observer<S, O, A, B>,
                          A, B> {

    void update(S subject, A argument, B argument2);
}
