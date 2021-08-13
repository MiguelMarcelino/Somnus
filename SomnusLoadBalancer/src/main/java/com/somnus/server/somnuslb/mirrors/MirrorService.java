package com.somnus.server.somnuslb.mirrors;

import com.somnus.server.somnuslb.mirrors.domain.Mirror;
import com.somnus.server.somnuslb.mirrors.dto.NewMirrorRegisterDTO;
import com.somnus.server.somnuslb.mirrors.repository.MirrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MirrorService extends MirrorObservable {

    private Logger logger = Logger.getLogger("MirrorInfoService");

    @Autowired
    private MirrorRepository mirrorRepository;

    public MirrorService() {
    }

    public void addMirror(NewMirrorRegisterDTO newMirror) {
        if (!this.mirrorRepository.getMirrorWithIpAddress(newMirror.getIpAddress()).isPresent())
            logger.info("The mirror with the ip address: " + newMirror.getIpAddress() + " has already been registered.");

        mirrorRepository.save(new Mirror(newMirror.getIpAddress(), System.currentTimeMillis()));
    }

}
