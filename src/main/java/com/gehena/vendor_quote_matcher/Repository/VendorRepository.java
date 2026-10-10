package com.gehena.vendor_quote_matcher.Repository;

import com.gehena.vendor_quote_matcher.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
