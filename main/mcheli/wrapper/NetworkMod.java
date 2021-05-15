package mcheli.wrapper;

import java.lang.annotation.*;

public @interface NetworkMod {
    boolean clientSideRequired();
    
    boolean serverSideRequired();
}
