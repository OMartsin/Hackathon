package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
import trandafyl.dev.hackathontest.repositories.AuctionLotRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Transactional
public class AuctionLotService {

    private final AuctionLotRepository auctionRepository;
    private final S3Service s3Service;
    private final AuthorizationValidator authValidator;
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public PageResponse<Page<AuctionLotResponse>> getAuctions(int pageNumber, int pageSize) {
        var auctions = auctionRepository.findAll(PageRequest.of(pageNumber, pageSize));

        return new PageResponse<>(auctions.map(this::mapToDTO), auctionRepository.count());
    }

    public Optional<AuctionLotResponse> getAuction(long id) {
        var auction = auctionRepository.findById(id);
        return auction.map(this::mapToDTO);
    }

    public AuctionLotResponse addAuction(AuctionLotRequest newAuction, List<MultipartFile> files) {
        var images = files.stream().map(s3Service::uploadFile).toList();
        var mappedAuction = mapFromDTO(newAuction, images);
        var savedAuction = auctionRepository.save(mappedAuction);

        return mapToDTO(savedAuction);
    }

    public AuctionLotResponse editAuction(long auctionId, AuctionLotEditRequest editedAuction, List<MultipartFile> files) {
        var auction = auctionRepository.findById(auctionId).orElseThrow();

        authValidator.checkEditAuthority(auction.getCreator());

        var images = Stream.concat(files.stream().map(s3Service::uploadFile), auction.getImageNames().stream()).toList();

        var mappedAuction = mapFromEditRequestDTO(editedAuction, auction, images);

        var savedAuction = auctionRepository.save(mappedAuction);

        return mapToDTO(savedAuction);
    }

    public void deleteAuction(long auctionId) {
        var auction = auctionRepository.findById(auctionId).orElseThrow();

        authValidator.checkEditAuthority(auction.getCreator());

        auction.getImageNames().forEach(s3Service::deleteFile);
        auctionRepository.deleteById(auctionId);
    }

    public AuctionLotListResponse getUserLots(long id) {
        var lots = auctionRepository.findAllByCreatorId(id);
        return mapToListDTO(lots);
    }
}
