package edu.fbansept.demo.controller;

import edu.fbansept.demo.dao.ReponsePossibleDao;
import edu.fbansept.demo.dao.ReponseUtilisateurDao;
import edu.fbansept.demo.model.ReponsePossible;
import edu.fbansept.demo.model.ReponseUtilisateur;
import edu.fbansept.demo.model.Utilisateur;
import edu.fbansept.demo.security.AppUserDetails;
import edu.fbansept.demo.security.IsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reponseUtilisateur")
public class ReponseUtilisateurController {

    @Autowired
    ReponseUtilisateurDao reponseUtilisateurDao;

    @Autowired
    ReponsePossibleDao reponsePossibleDao;

    @IsUser
    @PostMapping("")
    public ResponseEntity<ReponseUtilisateur> add(
            @RequestBody ReponseUtilisateur reponseUtilisateur,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        if (reponseUtilisateur.getReponsePossible() == null || reponseUtilisateur.getReponsePossible().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<ReponsePossible> reponsePossibleOptional = reponsePossibleDao.findById(reponseUtilisateur.getReponsePossible().getId());

        if (reponsePossibleOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilisateur utilisateur = userDetails.getUtilisateur();
        reponseUtilisateur.setUtilisateur(utilisateur);
        reponseUtilisateur.setReponsePossible(reponsePossibleOptional.get());
        reponseUtilisateurDao.save(reponseUtilisateur);

        return new ResponseEntity<>(reponseUtilisateur, HttpStatus.CREATED);
    }
}
