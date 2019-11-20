package com.excilys.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.excilys.demo.dto.IngredientDto;
import com.excilys.demo.exceptions.ElementNotFoundException;
import com.excilys.demo.model.Ingredient;
import com.excilys.demo.repository.IngredientRepository;

@Service
public class IngredientService {

  private final IngredientRepository ingredientRepository;

  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @Transactional
  public List<IngredientDto> getAll() {
    return ingredientRepository.getAll().stream().map(IngredientDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public Optional<IngredientDto> getById(Long id) {
    Optional<Ingredient> ingredient = ingredientRepository.getById(id);

    if (!ingredient.isPresent()) {
      throw new ElementNotFoundException();
    }

    return Optional.of(new IngredientDto(ingredient.get()));
  }

  @Transactional
  public Optional<Long> create(String name) {
    Ingredient ingredient = new Ingredient(name);
    return ingredientRepository.create(ingredient);
  }

  @Transactional
  public Optional<Ingredient> update(IngredientDto ingredientDto) {
    return ingredientRepository.update(ingredientDto.toIngredient());
  }

  @Transactional
  public void delete(IngredientDto ingredientDto) {
    ingredientRepository.delete(ingredientDto.toIngredient());
  }

  @Transactional
  public void deleteById(Long id) {
    ingredientRepository.deleteById(id);
  }
}
