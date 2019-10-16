# smida.com.ua
Існує реєстр акцій: 
текстове поле -  "Коментар"
цілочисельні поля - "Розмір статутного капіталу", "ЄДРПОУ установи", "Кількість"
десяткові поля - "Загальна номінальна вартість"*, "Номінальна вартість", "Сплачене державне мито"
дата - "Дата випуску"

*"Загальна номінальна вартість" рахується шляхом перемноження "Кількість" на "Номінальна вартість".

Необхідно:
1. Розробити функціонал створення запису нової акції.
2. Розробити функціонал редагування акції з обов'язковим збереженням змін.
3. Розробити функціонал, який буде повертати:
а) "публічні дані" - "ЄРДПОУ установи", "Кількість", "Загальна номінальна вартість", "Номінальна вартість" та "Дату випуску";
б) "приватні дані" - всю інформацію щодо акції та її змін.
4. Розробити функціонал для можливості перегляду всіх акцій однієї установи, як "публічно" так і "приватно".
5. Розробити функціонал фільтрації та сортування (з можливістю пейджинації).
