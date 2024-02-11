package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import trandafyl.dev.hackathontest.config.AuthorizationValidator;
import trandafyl.dev.hackathontest.dto.PageResponse;
import trandafyl.dev.hackathontest.dto.AuctionLotEditRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotListResponse;
import trandafyl.dev.hackathontest.dto.AuctionLotRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.mappers.AuctionLotMapper;
import trandafyl.dev.hackathontest.models.AuctionCategory;
import trandafyl.dev.hackathontest.models.AuctionLot;
import trandafyl.dev.hackathontest.repositories.AuctionLotRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class AuctionLotService {

    private final AuctionLotRepository auctionRepository;
    private final S3Service s3Service;
    private final AuthorizationValidator authValidator;
    private final AuctionLotMapper lotMapper;

    public PageResponse<Page<AuctionLotListResponse.AuctionLotPartialResponse>> getAuctions(int pageNumber, int pageSize, double minPrice, double maxPrice, List<AuctionCategory> categories) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<AuctionLot> auctions = (categories == null)
                ? auctionRepository.findByPriceRange(minPrice, maxPrice, pageRequest)
                : auctionRepository.findByCategoriesAndPriceRange(categories, minPrice, maxPrice, pageRequest);
        return new PageResponse<>(auctions.map(lotMapper::mapToPartialResponseDTO), auctionRepository.count());
    }

    public Optional<AuctionLotResponse> getAuction(long id) {
        var auction = auctionRepository.findById(id);
        return auction.map(lotMapper::mapToDTO);
    }

    public AuctionLotResponse addAuction(AuctionLotRequest newAuction, List<MultipartFile> files) {
        var images = files.stream().map(s3Service::uploadFile).toList();
        var mappedAuction = lotMapper.mapFromDTO(newAuction, images);
        var savedAuction = auctionRepository.save(mappedAuction);

        return lotMapper.mapToDTO(savedAuction);
    }

    public AuctionLotResponse editAuction(long auctionId, AuctionLotEditRequest editedAuction, List<MultipartFile> files) {
        var auction = auctionRepository.findById(auctionId).orElseThrow();

        authValidator.checkEditAuthority(auction.getCreator());

        var images = Stream.concat(files.stream().map(s3Service::uploadFile), auction.getImageNames().stream()).toList();

        var mappedAuction = lotMapper.mapFromEditRequestDTO(editedAuction, auction, images);

        var savedAuction = auctionRepository.save(mappedAuction);

        return lotMapper.mapToDTO(savedAuction);
    }

    public void deleteAuction(long auctionId) {
        var auction = auctionRepository.findById(auctionId).orElseThrow();

        authValidator.checkEditAuthority(auction.getCreator());

        auction.getImageNames().forEach(s3Service::deleteFile);
        auctionRepository.deleteById(auctionId);
    }

    public AuctionLotListResponse getUserLots(long id) {
        var lots = auctionRepository.findAllByCreatorId(id);
        return lotMapper.mapToListDTO(lots);
    }
}
