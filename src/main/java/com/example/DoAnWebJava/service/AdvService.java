package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.repositories.AdvRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdvService {

    @Autowired
    private AdvRepository advRepository;

    public List<Adv> getAllAdvs() {
        return advRepository.findAll();
    }

    public List<Adv> getActiveAdvs() {
        return advRepository.findByIsActivate(true);
    }

    public List<Adv> getInactiveAdvs() {
        return advRepository.findByIsActivate(false);
    }

    public Adv addAdv(Adv model) {
        model.setCreatedDate(new Date());
        model.setModifiedDate(new Date());
        return advRepository.save(model);
    }

    public Adv editAdv(int id) throws UserRegistrationException {
        Optional<Adv> optionalAdv = advRepository.findById(id);
        if (optionalAdv.isPresent()) {
            return optionalAdv.get();
        } else {
            throw new UserRegistrationException("Adv not found with id: " + id);
        }
    }

    public Adv editAdv(int id, Adv model) throws UserRegistrationException {
        Optional<Adv> optionalAdv = advRepository.findById(id);
        if (optionalAdv.isPresent()) {
            Adv adv = optionalAdv.get();
            adv.setTitle(model.getTitle());
            adv.setDescription(model.getDescription());
            adv.setImage(model.getImage());
            adv.setLink(model.getLink());
            adv.setActivate(model.isActivate());
            adv.setModifiedDate(new Date());
            return advRepository.save(adv);
        } else {
            throw new UserRegistrationException("Adv not found with id: " + id);
        }
    }


    public void deleteAdv(int id) throws UserRegistrationException {
        Optional<Adv> optionalAdv = advRepository.findById(id);
        if (optionalAdv.isPresent()) {
            Adv adv = optionalAdv.get();
            adv.setActivate(false);
            advRepository.save(adv);
        } else {
            throw new UserRegistrationException("Adv not found with id: " + id);
        }
    }

    public void deleteAllAdvs(List<Integer> ids) throws UserRegistrationException {
        List<Adv> advList = advRepository.findAllById(ids);
        if (!advList.isEmpty()) {
            advList.forEach(adv -> adv.setActivate(false));
            advRepository.saveAll(advList);
        } else {
            throw new UserRegistrationException("No Advs found with the given ids");
        }
    }

}
