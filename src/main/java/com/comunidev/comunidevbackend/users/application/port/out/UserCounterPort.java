package com.comunidev.comunidevbackend.users.application.port.out;

public interface UserCounterPort {
    void incrementSeguidores(String userId);
    void decrementSeguidores(String userId);

    void incrementSiguiendo(String userId);
    void decrementSiguiendo(String userId);

    void incrementConexiones(String userId);
    void decrementConexiones(String userId);
}
