package core.service;

import core.converter.CityConverter;
import core.domain.City;
import core.dto.CitiesDTO;
import core.dto.CityDTO;
import core.dto.PagingRequest;
import core.dto.PagingResponse;
import core.exceptions.BusManagementException;
import core.repository.IBusStationRepository;
import core.repository.ICityRepository;
import core.utils.RestPagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.lang.Math.max;

@Service
public class CityServiceImpl implements ICityService{
    private final ICityRepository cityRepo;
    private final IBusStationRepository busStationRepo;
    private final CityConverter cityConverter;

    public static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    public CityServiceImpl(ICityRepository cityRepo, IBusStationRepository busStationRepo, CityConverter cityConverter) {
        this.cityRepo = cityRepo;
        this.busStationRepo = busStationRepo;
        this.cityConverter = cityConverter;
    }


    @Override
    public void save(City city) {
        logger.trace("add city - method entered - name: " + city.getName() + ", population: " + city.getPopulation());

        cityRepo.save(city);

        logger.trace("add city - method finished");

    }

    @Override
    @Transactional
    public void update(Long id, String name, int population) {
        logger.trace("update city - method entered - id: " + id + ", name: " + name + ", population: " + population);

        cityRepo.findById(id)
                .ifPresentOrElse((c) -> {
                        c.setName(name);
                        c.setPopulation(population);
                    },
                    () -> {throw new BusManagementException("City not found!");}
        );

        logger.trace("update city - method finished");
    }

    @Override
    public void delete(Long id) {
        logger.trace("delete city - method entered - id: " + id);

        StreamSupport.stream(busStationRepo.findAll().spliterator(), false)
                .filter(station -> station.getCity().getId().equals(id))
                .findAny()
                .ifPresent((busStation) -> {
                    throw new BusManagementException("There are bus stations in the city!");
                });

        cityRepo.findById(id)
                .ifPresentOrElse((c) -> cityRepo.deleteById(c.getId()),
                        () -> {
                            throw new BusManagementException("City does not exist");
                        }
                    );

        logger.trace("delete city - method finished");
    }

    @Override
    public List<City> findAll() {
        logger.trace("findAll cities - method entered");

        List<City> cities = cityRepo.findAll();

        logger.trace("findAll cities: " + cities);

       return cities;
    }

    @Override
    public PagingResponse<CityDTO> findAll(PagingRequest pagingRequest) {
        if(RestPagingUtil.isRequestPaged(pagingRequest)){

            Page<City> page;
            page = this.cityRepo.findAll(RestPagingUtil.buildPageRequest(pagingRequest));

            List<City> cityList = page.getContent();
            List<CityDTO> cityDTOS = this.cityConverter.convertModelsToDTOsList(cityList);

            return new PagingResponse<>(
                    page.getTotalElements(),
                    (long) page.getNumber(),
                    (long) page.getNumberOfElements(),
                    RestPagingUtil.buildPageRequest(pagingRequest).getOffset(),
                    (long) page.getTotalPages(),
                    cityDTOS);

        } else {
            List<City> cities = this.cityRepo.findAll();
            List<CityDTO> cityDTOList = this.cityConverter.convertModelsToDTOsList(cities);
            return new PagingResponse<>(
                    (long) cityDTOList.size(),
                    0L,
                    0L,
                    0L,
                    0L,
                    cityDTOList
            );
        }
    }


    @Override
    public City findCityByName(String name) {
        return this.cityRepo.findCityByName(name);
    }
}








//    @Override
//    public PagingResponse<CityDTO> findAll(Specification<City> spec, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public CitiesDTO findAll(Specification<City> spec, Sort sort) {
//
//        List<City> cities;
//
//        cities = this.cityRepo.findAll(spec, sort);
//
//        return new CitiesDTO(cityConverter.convertModelsToDTOsList(cities));
//    }
