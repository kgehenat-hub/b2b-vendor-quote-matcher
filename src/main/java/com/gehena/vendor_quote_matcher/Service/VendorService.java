package com.gehena.vendor_quote_matcher.Service;

import com.gehena.vendor_quote_matcher.Repository.VendorRepository;
import com.gehena.vendor_quote_matcher.entity.Vendor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }


}

