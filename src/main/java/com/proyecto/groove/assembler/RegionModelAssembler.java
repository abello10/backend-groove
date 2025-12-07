package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.RegionControllerV2;

import com.proyecto.groove.model.Region;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<Region,EntityModel<Region>>{

    @Override
    public EntityModel<Region> toModel(Region region){
        return EntityModel.of(region,
             linkTo(methodOn(RegionControllerV2.class).getRegionById(region.getId())).withSelfRel(),
            linkTo(methodOn(RegionControllerV2.class).getAllRegion()).withRel("regiones"),
            linkTo(methodOn(RegionControllerV2.class).updateRegion(region.getId(),region)).withRel("actualizar"),
            linkTo(methodOn(RegionControllerV2.class).deleteRegion(region.getId())).withRel("eliminar"),
            linkTo(methodOn(RegionControllerV2.class).partialUpdateRegion(region.getId(), region)).withRel("reemplazar una línea")
           
           
        );
    }
}
