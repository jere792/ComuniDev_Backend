package com.comunidev.comunidevbackend.user.application.service;

import com.comunidev.comunidevbackend.user.application.port.out.UserCounterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCounterService {

    private final UserCounterPort userCounterPort;

    public void onFollow(String seguidoId, String seguidorId) {
        userCounterPort.incrementSeguidores(seguidoId);
        userCounterPort.incrementSiguiendo(seguidorId);
    }

    public void onUnfollow(String seguidoId, String seguidorId) {
        userCounterPort.decrementSeguidores(seguidoId);
        userCounterPort.decrementSiguiendo(seguidorId);
    }

    public void onConnectionAccepted(String userId1, String userId2) {
        userCounterPort.incrementConexiones(userId1);
        userCounterPort.incrementConexiones(userId2);
    }

    public void onConnectionRemoved(String userId1, String userId2) {
        userCounterPort.decrementConexiones(userId1);
        userCounterPort.decrementConexiones(userId2);
    }
}
