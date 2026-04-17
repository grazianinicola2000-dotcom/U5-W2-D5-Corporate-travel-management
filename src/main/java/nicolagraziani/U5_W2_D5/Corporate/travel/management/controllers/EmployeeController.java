package nicolagraziani.U5_W2_D5.Corporate.travel.management.controllers;

import nicolagraziani.U5_W2_D5.Corporate.travel.management.entities.Employee;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions.ValidationException;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.payloads.EmployeeDTO;
import nicolagraziani.U5_W2_D5.Corporate.travel.management.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //    GET ALL
    @GetMapping
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "surname") String sortBy) {
        return this.employeeService.findAll(page, size, sortBy);
    }

    //    POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.employeeService.saveEmployee(body);
    }

    //    GET BY ID
    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId) {
        return this.employeeService.findEmployeeById(employeeId);
    }

    //    ADD IMAGE
    @PatchMapping("/{employeeId}/profileImg")
    public void uploadImage(@RequestParam("profile_img") MultipartFile file, @PathVariable UUID employeeId) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        this.employeeService.profileImgUpload(file, employeeId);
    }

    //    PUT
    @PutMapping("/{employeeId}")
    public Employee getEmployeeByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.employeeService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    //    DELETE
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getEmployeeByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findEmployeeByIdAndDelete(employeeId);
    }

}
