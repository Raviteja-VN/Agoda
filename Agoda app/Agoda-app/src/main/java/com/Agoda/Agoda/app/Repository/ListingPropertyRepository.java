package com.Agoda.Agoda.app.Repository;

import com.Agoda.Agoda.app.Entity.ListingProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingPropertyRepository extends JpaRepository<ListingProperty,Long> {
}
