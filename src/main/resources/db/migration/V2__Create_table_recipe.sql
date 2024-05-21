create table recipe (
    recipe_id serial primary key,
    dish varchar(255),
    duration_min int,
    ingredients text,
    instructions text
);

INSERT INTO recipe (dish, duration_min, ingredients, instructions) VALUES
    ('Spaghetti Carbonara', 20, 'Spaghetti, Eggs, Pancetta, Parmesan Cheese, Black Pepper, Salt', '1. Boil spaghetti. 2. Cook pancetta. 3. Mix eggs and cheese. 4. Combine all ingredients.'),
    ('Chicken Curry', 45, 'Chicken, Onion, Garlic, Ginger, Tomatoes, Coconut Milk, Curry Powder, Cilantro', '1. Cook onions, garlic, and ginger. 2. Add chicken and brown. 3. Add tomatoes and coconut milk. 4. Simmer and add curry powder. 5. Garnish with cilantro.'),
    ('Vegetable Stir Fry', 30, 'Broccoli, Carrots, Bell Peppers, Soy Sauce, Garlic, Ginger, Olive Oil, Sesame Seeds', '1. Chop vegetables. 2. Stir fry garlic and ginger. 3. Add vegetables and soy sauce. 4. Cook until tender. 5. Sprinkle with sesame seeds.'),
    ('Beef Tacos', 25, 'Ground Beef, Taco Shells, Lettuce, Tomato, Cheese, Sour Cream, Taco Seasoning', '1. Cook ground beef with taco seasoning. 2. Prepare taco shells. 3. Assemble tacos with beef, lettuce, tomato, cheese, and sour cream.'),
    ('Pancakes', 15, 'Flour, Eggs, Milk, Baking Powder, Sugar, Butter, Maple Syrup', '1. Mix flour, eggs, milk, baking powder, and sugar. 2. Cook on a hot griddle. 3. Serve with butter and maple syrup.');
