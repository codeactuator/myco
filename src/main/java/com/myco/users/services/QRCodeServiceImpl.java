package com.myco.users.services;

import com.myco.users.entities.QRCode;
import com.myco.users.exceptions.ApplicationException;
import com.myco.users.repositories.QRCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class QRCodeServiceImpl implements QRCodeService{

    private final QRCodeRepository qrCodeRepository;

    public QRCodeServiceImpl(QRCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    @Override
    public QRCode save(QRCode qrCode) throws ApplicationException {
        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode remove(long id) throws ApplicationException {
        QRCode qrCode = qrCodeRepository.findById(id).orElseThrow();
        qrCodeRepository.deleteById(id);
        return qrCode;
    }

    @Override
    public QRCode find(Long id) {
        return qrCodeRepository.findById(id).orElseThrow();
    }
}
