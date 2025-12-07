package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.RegionRepository;
import java.util.List;
import jakarta.transaction.Transactional;
import com.proyecto.groove.model.Region;;

@Service
@Transactional
@SuppressWarnings("null")
public class RegionService {
    
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaService comunaService;

    public List<Region> findAll(){
        return regionRepository.findAll();
    }

    public Region findById(Integer id){
        Region region = regionRepository.findById(id).orElse(null);
        return region;
    }

    public Region save(Region region){
        return regionRepository.save(region);
    }

    public Region partialUpdate(Region region){
        Region existingRegion = regionRepository.findById(region.getId()).orElse(null);
        if (existingRegion != null){
            if (region.getNombre() != null){
                existingRegion.setNombre(region.getNombre());
            }
            return regionRepository.save(existingRegion);
        }
        return null;
    }

    public void deleteById(Integer id) {
        comunaService.deleteByRegionId(id);
        regionRepository.deleteById(id);
    }
}
