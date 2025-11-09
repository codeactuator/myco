package com.myco.users.services;

public interface QRCodeGeneratorService {

    byte[] create(String qrContent, int width, int height);
}
