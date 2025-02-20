package ru.practicum.android.diploma.data.dto.responseUnits

data class VacancyDto(
    val id: String,
    val department: String,
    val name: String,
    val area: VacancyArea, // Регион
    val employer: Employer, // Работодатель
    val salary: Salary?, // Зарплата
    val type: VacancyType, // Тип вакансии
)
