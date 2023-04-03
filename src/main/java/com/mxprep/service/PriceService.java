package com.mxprep.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface PriceService {

    void save(MultipartFile file) throws IOException;
}
