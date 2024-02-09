package trandafyl.dev.hackathontest.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.search.backend.lucene.LuceneExtension;
import org.hibernate.search.backend.lucene.search.query.LuceneSearchQuery;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.dto.PageResponse;
import trandafyl.dev.hackathontest.mappers.AuctionLotMapper;
import trandafyl.dev.hackathontest.models.AuctionLot;

@AllArgsConstructor
@Service
@Log4j2
@Transactional
public class SearchService {
    private EntityManager entityManager;
    private AuctionLotMapper lotMapper;

    public PageResponse<Streamable<AuctionLotResponse>> searchWithFuzzy(String searchTerm, long pageNumber, long pageSize) {
        SearchSession searchSession = Search.session(entityManager);

        LuceneSearchQuery<AuctionLot> query = searchSession
                .search(AuctionLot.class)
                .extension(LuceneExtension.get())
                .where(f -> f.bool()
                        .should(f.match().field("name").matching(searchTerm).fuzzy())
                        .should(f.wildcard().field("name").matching("*" + searchTerm + "*"))
                )
                .toQuery();

        var auctionsPage = new PageImpl<>(query
                .fetchAllHits()
                .stream()
                .skip(pageNumber *pageSize)
                .limit(pageSize)
                .toList());

        return new PageResponse<>(auctionsPage.map(lotMapper::mapToDTO), query.fetchTotalHitCount());
    }
}
