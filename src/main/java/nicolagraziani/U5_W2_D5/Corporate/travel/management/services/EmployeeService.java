package nicolagraziani.U5_W2_D5.Corporate.travel.management.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Employee;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.BadRequestException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.NotFoundException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads.EmployeeDTO;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.repositories.EmplyeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {
    private final Cloudinary cloudinaryUploader;
    private EmplyeeRepository emplyeeRepository;

    public EmployeeService(EmplyeeRepository emplyeeRepository, Cloudinary cloudinaryUploader) {
        this.emplyeeRepository = emplyeeRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    //    SAVE EMPLOYEE
    public Employee saveEmployee(EmployeeDTO body) {
        if (this.emplyeeRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'indirizzo mail " + body.email() + " è già in uso!");
        }
        if (this.emplyeeRepository.existsByUsername(body.username())) {
            throw new BadRequestException("L'username " + body.username() + " è già in uso!");
        }
        Employee newEmployee = new Employee(body.username(), body.name(), body.surname(), body.email());
        this.emplyeeRepository.save(newEmployee);
        log.info("Il dipendente {} {} è stato registrato correttamente", body.surname(), body.name());
        return newEmployee;
    }

    //    FIND ALL EMPLOYEE WITH PAGINATION
    public Page<Employee> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.emplyeeRepository.findAll(pageable);
    }

    //    FIND BY ID
    public Employee findEmployeeById(UUID employeeId) {
        return this.emplyeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    //    UPLOAD IMG
    public void profileImgUpload(MultipartFile file, UUID employeeId) {
        Employee found = this.findEmployeeById(employeeId);
        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");
            found.setAvatarImg(url);
            this.emplyeeRepository.save(found);
            log.info("L'Immagine profilo del dipendente con id {} è stata aggiornata correttamente con la seguente immagine: {}", employeeId, url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    FIND BY ID AND UPDATE
    public Employee findEmployeeByIdAndUpdate(UUID employeeId, EmployeeDTO body) {
        Employee found = this.findEmployeeById(employeeId);
        if (!found.getEmail().equals(body.email())) {
            if (this.emplyeeRepository.existsByEmail(body.email()))
                throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");
        }
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setUsername(body.username());

        Employee saved = this.emplyeeRepository.save(found);
        log.info("Il dipendente {} {} è stato modificato con successo", saved.getName(), saved.getSurname());
        return saved;
    }

    //    DELETE
    public void findEmployeeByIdAndDelete(UUID employeeId) {
        Employee found = this.findEmployeeById(employeeId);
        this.emplyeeRepository.delete(found);
        log.info("Il dipendente {} {} è stato eliminato correttamente", found.getSurname(), found.getName());
    }

}
