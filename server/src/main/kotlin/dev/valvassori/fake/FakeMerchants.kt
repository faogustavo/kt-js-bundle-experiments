package dev.valvassori.fake

import dev.valvassori.domain.Address
import dev.valvassori.domain.Item
import dev.valvassori.domain.Merchant
import dev.valvassori.domain.WorkingHours

object FakeMerchants {
    val allMerchants: Map<String, Merchant> by lazy {
        listOf(
            mcDonalds,
            subway,
            walmart,
            wholeFoods
        ).associateBy { it.id }
    }

    // Restaurant merchants
    val mcDonalds = Merchant(
        id = "4e782760-d2e1-4cdf-a9c1-dc9ffddbadfe",
        name = "McDonald's",
        type = Merchant.Type.Restaurant,
        minimumOrder = 1000, // $10.00
        isOpen = true,
        workingHours = listOf(
            // Open 24/7
            WorkingHours(0, 0, 1440), // Sunday
            WorkingHours(1, 0, 1440), // Monday
            WorkingHours(2, 0, 1440), // Tuesday
            WorkingHours(3, 0, 1440), // Wednesday
            WorkingHours(4, 0, 1440), // Thursday
            WorkingHours(5, 0, 1440), // Friday
            WorkingHours(6, 0, 1440), // Saturday
        ),
        deliveryTime = 30, // 30 minutes
        deliveryFee = 299, // $2.99
        rating = 42, // 4.2/5.0
        ratingCount = 5243,
        imageUrl = "https://picsum.photos/seed/i/200",
        address = Address(
            id = "32375485-776f-41f6-919e-e09730dfeccd",
            name = "McDonald's Downtown",
            latitude = 37.7749,
            longitude = -122.4194,
            addressLine1 = "235 Front St",
            addressLine2 = "",
            city = "San Francisco",
            state = "CA",
            zip = "94111"
        ),
        phoneNumber = "(415) 986-0793",
        category = "Fast Food",
        menu = listOf(
            Merchant.MenuCategory(
                id = "e224d710-3515-41d4-b568-28cd2e1d1c5f",
                name = "Burgers",
                description = "Classic McDonald's burgers",
                imageUrl = "https://picsum.photos/seed/da90224c-7955-4e0c-b5e2-c294322ffddc/200",
                items = listOf(
                    Item(
                        id = "1c84d899-3b7d-48ce-b33d-123a560bc593",
                        name = "Big Mac",
                        description = "The iconic Big Mac with two beef patties, special sauce, lettuce, cheese, pickles, and onions on a sesame seed bun.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/ba6beb2f-13fb-43b5-a272-891841df45d4/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "a26db011-4981-4093-bf41-a077b6d848ef",
                        name = "Quarter Pounder with Cheese",
                        description = "A quarter pound of fresh beef with cheese, onions, pickles, and condiments on a sesame seed bun.",
                        price = 649, // $6.49
                        imageUrl = "https://picsum.photos/seed/0236dd71-0166-4b22-9675-5bc980c7769c/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "8d72b75c-2101-4c7b-95bd-c7261e473a59",
                        name = "Double Cheeseburger",
                        description = "Two beef patties with cheese, onions, pickles, and condiments on a regular bun.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/35a5ffcc-e4eb-4823-ac97-53f8baaf1a68/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "0cd267ff-4406-4ef8-b019-3d8c371633f9",
                        name = "Hamburger",
                        description = "A classic hamburger with a beef patty, onions, pickles, and condiments on a regular bun.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/fb0f966e-8a13-4a2c-abf4-14ce9f66358c/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "2abcc37c-cabe-4896-8230-08cfc1651ac7",
                name = "Chicken & Sandwiches",
                description = "Chicken sandwiches and nuggets",
                imageUrl = "https://picsum.photos/seed/5dd6ac21-6bab-42e3-ac81-e68b948502e1/200",
                items = listOf(
                    Item(
                        id = "0ef8bdea-3346-41bb-b50c-1e9b6d93cf8b",
                        name = "McChicken",
                        description = "A crispy chicken patty with lettuce and mayo on a regular bun.",
                        price = 249, // $2.49
                        imageUrl = "https://picsum.photos/seed/2bf9e2d8-5f18-46d0-9891-f9007a45aac8/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "80162d51-488b-43cf-851a-1722a78be7a2",
                        name = "Chicken McNuggets (10 pc)",
                        description = "Ten pieces of crispy chicken nuggets with your choice of dipping sauce.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/aa33cd0f-85f4-4907-a499-6e4ee8744c24/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "9332f7b1-8131-454e-92e0-dcf8fb526082",
                        name = "Crispy Chicken Sandwich",
                        description = "A crispy chicken fillet with pickles on a potato roll.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/3512e177-ab35-4d4f-869f-8d3522da42ae/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "43ee2a3c-0f7f-4a80-a99e-a58e69eb5af2",
                name = "Sides & Fries",
                description = "French fries and sides",
                imageUrl = "https://picsum.photos/seed/ce9e84ed-c5cf-458b-91f1-4272974bb4af/200",
                items = listOf(
                    Item(
                        id = "7da1673c-fe0a-4658-9581-0d3f9c7d1205",
                        name = "Medium French Fries",
                        description = "Golden, crispy french fries, medium size.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/252af4a1-ae21-4d4c-9555-0ef495bfb4b1/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "ec39f66f-781c-4f0b-bb5f-9c65ad4062f7",
                        name = "Apple Pie",
                        description = "A crispy pastry filled with apple filling.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/d6e63dea-d42b-4193-929f-f51f838ac5d8/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "27c288a1-92c0-42d8-a793-7ec4b8ae4a45",
                name = "Beverages",
                description = "Soft drinks, coffee, and shakes",
                imageUrl = "https://picsum.photos/seed/c4f95259-1004-4691-b650-73d9d7bfe744/200",
                items = listOf(
                    Item(
                        id = "3882d97e-0135-44f9-9e3a-9905a472c557",
                        name = "Medium Coca-Cola",
                        description = "A medium-sized Coca-Cola soft drink.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/df1d3a90-f3cf-42a5-b117-87d1d7628493/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "9e7b6d41-a70f-43b2-bf31-06a8a0e1b159",
                        name = "Chocolate Shake",
                        description = "A creamy chocolate milkshake.",
                        price = 349, // $3.49
                        imageUrl = "https://picsum.photos/seed/cd60e19c-872e-4f6a-952d-352abe147120/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "d059d889-b7d2-4bd4-bea2-90532ff48d3c",
                        name = "Premium Roast Coffee",
                        description = "A medium-sized hot coffee.",
                        price = 149, // $1.49
                        imageUrl = "https://picsum.photos/seed/787a2dce-040e-4201-a141-6ff88ca987c6/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            )
        ),
    )

    val subway = Merchant(
        id = "81c870e9-e088-4ebf-8a01-aef9f4dcff5b",
        name = "Subway",
        type = Merchant.Type.Restaurant,
        minimumOrder = 800, // $8.00
        isOpen = true,
        workingHours = listOf(
            // 8 AM to 10 PM
            WorkingHours(0, 480, 1320), // Sunday
            WorkingHours(1, 480, 1320), // Monday
            WorkingHours(2, 480, 1320), // Tuesday
            WorkingHours(3, 480, 1320), // Wednesday
            WorkingHours(4, 480, 1320), // Thursday
            WorkingHours(5, 480, 1320), // Friday
            WorkingHours(6, 480, 1320), // Saturday
        ),
        deliveryTime = 25, // 25 minutes
        deliveryFee = 199, // $1.99
        rating = 38, // 3.8/5.0
        ratingCount = 3127,
        imageUrl = "https://picsum.photos/seed/1cf8f3eb-b36c-4e60-a656-87ea4a362915/200",
        address = Address(
            id = "8edd001c-2e77-415e-ada2-5a8a1e92a5a2",
            name = "Subway Market Street",
            latitude = 37.7857,
            longitude = -122.4057,
            addressLine1 = "101 Market St",
            addressLine2 = "",
            city = "San Francisco",
            state = "CA",
            zip = "94105"
        ),
        phoneNumber = "(415) 543-5300",
        category = "Sandwiches",
        menu = listOf(
            Merchant.MenuCategory(
                id = "af9d129f-8021-4ee4-b148-063e18736381",
                name = "Footlong Subs",
                description = "12-inch submarine sandwiches",
                imageUrl = "https://picsum.photos/seed/47a5c163-d369-46a1-b5a4-316c2813b71e/200",
                items = listOf(
                    Item(
                        id = "991b75f9-d06d-4073-98e7-d1136d588701",
                        name = "Italian B.M.T.® Footlong",
                        description = "The Italian B.M.T.® sandwich is filled with Genoa salami, spicy pepperoni, and Black Forest ham.",
                        price = 899, // $8.99
                        imageUrl = "https://picsum.photos/seed/e61ec945-d821-4bd2-a2d6-8a8fce6d3672/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "aefd5111-dbd9-4a1f-a385-1e331b70a3e1",
                        name = "Meatball Marinara Footlong",
                        description = "The Meatball Marinara sandwich is drenched in irresistible marinara sauce and stuffed with meatballs.",
                        price = 799, // $7.99
                        imageUrl = "https://picsum.photos/seed/5a1a8357-e943-4f8a-8d7e-a9c0c500db1b/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "0688faa9-81cf-4f72-881d-e3430e760485",
                        name = "Tuna Footlong",
                        description = "Our tasty Tuna sandwich is simply sumptuous with flaked tuna, mixed with mayo, and topped with your choice of veggies.",
                        price = 849, // $8.49
                        imageUrl = "https://picsum.photos/seed/cb7841a0-0b96-45ac-8a84-77aa51a80172/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7e556094-3e08-4412-bf64-ca16c6dae46a",
                        name = "Veggie Delite® Footlong",
                        description = "The Veggie Delite® sandwich is crispy, crunchy, vegetarian perfection with lettuce, tomatoes, green peppers, cucumbers, and onions.",
                        price = 699, // $6.99
                        imageUrl = "https://picsum.photos/seed/11c2831f-cca3-4472-927f-169d61482ba9/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "29a4fce9-4f51-4982-8bb0-7248aab7bb52",
                name = "6-inch Subs",
                description = "6-inch submarine sandwiches",
                imageUrl = "https://picsum.photos/seed/4a6a9a65-fa10-4dad-949a-951b7e84c55f/200",
                items = listOf(
                    Item(
                        id = "82d7602e-a45d-44e2-afd9-5b2dbf11660f",
                        name = "Italian B.M.T.® 6-inch",
                        description = "The Italian B.M.T.® sandwich is filled with Genoa salami, spicy pepperoni, and Black Forest ham.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/a0e2c904-0891-4f2f-8aa2-25248a29c7ac/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "4b3edeb1-8124-45af-b669-87ab05be5e18",
                        name = "Meatball Marinara 6-inch",
                        description = "The Meatball Marinara sandwich is drenched in irresistible marinara sauce and stuffed with meatballs.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/ea953938-9e3e-48cd-9080-2e1ade8f954d/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "fb08ada2-4be9-44d8-b609-1c64481b78af",
                        name = "Tuna 6-inch",
                        description = "Our tasty Tuna sandwich is simply sumptuous with flaked tuna, mixed with mayo, and topped with your choice of veggies.",
                        price = 549, // $5.49
                        imageUrl = "https://picsum.photos/seed/a14ca654-23b1-4875-90b5-9435e8382c10/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "b013965f-7313-4a6c-b50d-920ff8aeb182",
                        name = "Veggie Delite® 6-inch",
                        description = "The Veggie Delite® sandwich is crispy, crunchy, vegetarian perfection with lettuce, tomatoes, green peppers, cucumbers, and onions.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/79f766e4-008a-4a90-a45c-a61fd0cf2075/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "520679a7-c9f8-48d0-9cd5-aea9dfceb532",
                name = "Salads",
                description = "Fresh salads with your favorite sub ingredients",
                imageUrl = "https://picsum.photos/seed/f529bd1d-2d44-4eaa-8ddc-4caf45a7414d/200",
                items = listOf(
                    Item(
                        id = "1fc269ac-b3e2-4d81-b4f6-dbb7f9310721",
                        name = "Italian B.M.T.® Salad",
                        description = "The Italian B.M.T.® salad is a flavor-packed salad with Genoa salami, spicy pepperoni, and Black Forest ham on a bed of fresh veggies.",
                        price = 799, // $7.99
                        imageUrl = "https://picsum.photos/seed/4a242730-ab02-42e4-ab25-fee76972ac27/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "09ced516-90b9-408d-b5dc-32f657767c70",
                        name = "Tuna Salad",
                        description = "Our tasty Tuna salad is simply sumptuous with flaked tuna, mixed with mayo, on a bed of fresh veggies.",
                        price = 749, // $7.49
                        imageUrl = "https://picsum.photos/seed/99853344-9243-4616-a024-0c799acd06f9/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "aa9de3e1-fa18-4eb5-bf38-4de1a4296c87",
                name = "Sides & Drinks",
                description = "Complement your meal with sides and drinks",
                imageUrl = "https://picsum.photos/seed/61cb113d-b0ca-45b7-a956-1d309da79ffe/200",
                items = listOf(
                    Item(
                        id = "f77836dc-2ab3-4511-ba55-1bca3dbb2032",
                        name = "Chocolate Chip Cookie",
                        description = "Chocolate chip cookie baked fresh daily.",
                        price = 99, // $0.99
                        imageUrl = "https://picsum.photos/seed/5fb32e14-23c4-4c08-9f21-9d5c5856e97a/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "ec36f1da-190e-4ad3-aeb6-7f4cd1a96241",
                        name = "Medium Fountain Drink",
                        description = "Medium-sized fountain drink of your choice.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/cfe8ba05-c1f4-4641-9531-3dadbb0b7281/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "d6f90099-5c68-4ed5-9997-5052b293e857",
                        name = "Lay's® Classic Potato Chips",
                        description = "Crispy Lay's® potato chips.",
                        price = 149, // $1.49
                        imageUrl = "https://picsum.photos/seed/7a4f8114-7b46-4193-be2c-1e707262af4b/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            )
        ),
    )

    // Supermarket merchants
    val walmart = Merchant(
        id = "ed1641ca-06c5-4136-8ccc-b0b655e1b80d",
        name = "Walmart",
        type = Merchant.Type.Supermarket,
        minimumOrder = 2000, // $20.00
        isOpen = true,
        workingHours = listOf(
            // 6 AM to 11 PM
            WorkingHours(0, 360, 1380), // Sunday
            WorkingHours(1, 360, 1380), // Monday
            WorkingHours(2, 360, 1380), // Tuesday
            WorkingHours(3, 360, 1380), // Wednesday
            WorkingHours(4, 360, 1380), // Thursday
            WorkingHours(5, 360, 1380), // Friday
            WorkingHours(6, 360, 1380), // Saturday
        ),
        deliveryTime = 60, // 60 minutes
        deliveryFee = 499, // $4.99
        rating = 40, // 4.0/5.0
        ratingCount = 8765,
        imageUrl = "https://picsum.photos/seed/998a7330-6134-49fb-8c1b-789d039d9fde/200",
        address = Address(
            id = "489ab30b-8ea6-4a1b-a41a-995239d0b23a",
            name = "Walmart Supercenter",
            latitude = 37.7304,
            longitude = -122.4765,
            addressLine1 = "1400 Ocean Ave",
            addressLine2 = "",
            city = "San Francisco",
            state = "CA",
            zip = "94112"
        ),
        phoneNumber = "(415) 333-0083",
        category = "Supermarket",
        menu = listOf(
            Merchant.MenuCategory(
                id = "ff2f75a3-15bd-4694-acbf-02ee56ae18ef",
                name = "Fresh Produce",
                description = "Fresh fruits and vegetables",
                imageUrl = "https://picsum.photos/seed/a3e8b72f-fe8a-4d89-8991-16a9c777b0ef/200",
                items = listOf(
                    Item(
                        id = "a96461c4-9816-42f4-956c-4eff7cb067ec",
                        name = "Bananas (bunch)",
                        description = "Fresh bananas, approximately 5-7 per bunch.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/98c11039-a11c-49ec-80d5-d78bf1157460/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "42ea7227-2bac-4128-9e79-7c6caa3dc9a7",
                        name = "Gala Apples (1 lb)",
                        description = "Fresh Gala apples, sold by the pound.",
                        price = 149, // $1.49
                        imageUrl = "https://picsum.photos/seed/6e3aa648-a9ee-4d5c-ab19-74ab5a3e0f7e/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "a1184272-1e62-4b1e-857a-e1cb034138cc",
                        name = "Hass Avocados (each)",
                        description = "Ripe Hass avocados, sold individually.",
                        price = 129, // $1.29
                        imageUrl = "https://picsum.photos/seed/4c06bc0b-44a0-4a63-9c18-fa7c7b65499b/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "74fd0a8e-aff3-4457-8285-7c5d57eb047a",
                        name = "Roma Tomatoes (1 lb)",
                        description = "Fresh Roma tomatoes, sold by the pound.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/784c390f-d151-49d4-959b-0414168ad596/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "fb210acc-0f0e-4eae-80c9-ff8c831a293e",
                name = "Dairy & Eggs",
                description = "Milk, cheese, eggs, and other dairy products",
                imageUrl = "https://picsum.photos/seed/202cbebc-bff9-466c-b17f-b978328eb05f/200",
                items = listOf(
                    Item(
                        id = "1dedfc54-1480-4121-bf16-3118ee274693",
                        name = "Whole Milk (1 gallon)",
                        description = "1 gallon of fresh whole milk.",
                        price = 349, // $3.49
                        imageUrl = "https://picsum.photos/seed/6c13a404-1487-497c-8403-e2a24c3bf584/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "5f193abe-2713-4089-bc51-59209ad79241",
                        name = "Large Eggs (dozen)",
                        description = "One dozen large grade A eggs.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/52fc973e-f116-41ef-ba0a-3bdb43344a54/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "1cdae0c8-81ff-4ca0-9a85-09cc1637855b",
                        name = "Cheddar Cheese (8 oz)",
                        description = "8 oz block of sharp cheddar cheese.",
                        price = 249, // $2.49
                        imageUrl = "https://picsum.photos/seed/7f29fa0c-c964-4539-abe4-474a1754688f/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "716d95db-d2d4-4d65-87a7-2234c16c6cac",
                        name = "Greek Yogurt (32 oz)",
                        description = "32 oz container of plain Greek yogurt.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/1225409d-d45b-4119-aa78-76a12d8010ba/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "e06ff0f3-15ac-4078-8e10-19c36312c15e",
                name = "Meat & Seafood",
                description = "Fresh meat and seafood products",
                imageUrl = "https://picsum.photos/seed/5fe2aa71-1949-4529-8372-ab68ab923b65/200",
                items = listOf(
                    Item(
                        id = "b8d98a3f-55f6-4363-b19c-c5a8d9e4ac0a",
                        name = "Ground Beef (1 lb)",
                        description = "1 pound of 80/20 ground beef.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/b616229f-282d-4002-80d5-636ca061bdca/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "fe1b4316-b949-4675-b27f-f1c5c6f895e5",
                        name = "Boneless Chicken Breast (1 lb)",
                        description = "1 pound of boneless, skinless chicken breast.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/b1fa5bbb-28bb-4d36-9b03-4a2930ffed31/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "2daba270-2a2c-4a7a-9104-01d4de0dc95b",
                        name = "Atlantic Salmon Fillet (1 lb)",
                        description = "1 pound of fresh Atlantic salmon fillet.",
                        price = 999, // $9.99
                        imageUrl = "https://picsum.photos/seed/757c8f5f-b64d-4d15-81f3-691b20e69772/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "017e9f1e-0da5-4c6a-ad86-48936d956999",
                        name = "Bacon (16 oz)",
                        description = "16 oz package of sliced bacon.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/026db2c1-1871-4fd5-b263-22d630f78941/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "0cfe8cab-627c-465b-b3aa-521fbbe94110",
                name = "Bakery",
                description = "Fresh bread and baked goods",
                imageUrl = "https://picsum.photos/seed/2d3f081d-457a-4944-afb9-0697de95e063/200",
                items = listOf(
                    Item(
                        id = "50ff7d45-f681-4d30-8bae-6bf961306058",
                        name = "White Bread (20 oz loaf)",
                        description = "20 oz loaf of sliced white bread.",
                        price = 199, // $1.99
                        imageUrl = "https://picsum.photos/seed/6c483837-d673-4ae7-9506-a10176476f64/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "90cc8f12-6b62-4e6f-bcf1-dc3a30a1ef9a",
                        name = "Plain Bagels (6 count)",
                        description = "6 count package of plain bagels.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/2ba91cf9-dae7-4211-a131-fedccb5e969f/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "0accd8fa-d3b4-4eaa-86b5-15570754de74",
                        name = "Blueberry Muffins (4 count)",
                        description = "4 count package of blueberry muffins.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/01c6c281-3780-4dde-9c97-67bc139a6fab/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "0fbcc23b-4ff5-4ce7-9eb9-2aaec02b9457",
                name = "Pantry Staples",
                description = "Essential pantry items",
                imageUrl = "https://picsum.photos/seed/49b47ad9-7cad-48c3-8283-49738ca6374b/200",
                items = listOf(
                    Item(
                        id = "0ef9650d-be3b-415f-9cca-c38ac6cb5f55",
                        name = "White Rice (5 lb)",
                        description = "5 pound bag of long grain white rice.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/22430a79-3ab2-4cf0-903d-eaff8a4de72c/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "9325f5aa-1a6e-43f0-826b-0ea641bfeccd",
                        name = "Spaghetti Pasta (16 oz)",
                        description = "16 oz package of spaghetti pasta.",
                        price = 149, // $1.49
                        imageUrl = "https://picsum.photos/seed/fd975e44-9796-41f0-8a8c-994f6da92935/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "accfb96b-e9c6-477a-8135-0d629a2dcc43",
                        name = "Cheerios Cereal (18 oz)",
                        description = "18 oz box of Cheerios cereal.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/e0f6b1a0-fa6b-4a5c-828f-1048daabe521/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "86da082a-7d30-4791-94a6-0ebc9b79114d",
                        name = "Peanut Butter (16 oz)",
                        description = "16 oz jar of creamy peanut butter.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/864fbc5c-571e-43dd-978e-845c864caa44/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "871db799-9697-4bd8-a95f-52f916750f76",
                        name = "Tomato Pasta Sauce (24 oz)",
                        description = "24 oz jar of tomato pasta sauce.",
                        price = 249, // $2.49
                        imageUrl = "https://picsum.photos/seed/b27fb72f-e40c-4102-a03c-c23908659d23/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "6d713b6b-327a-4586-9c6d-6c2daeefeb1b",
                name = "Beverages",
                description = "Drinks and beverages",
                imageUrl = "https://picsum.photos/seed/dc889baf-7b5c-47c3-a946-4941f12e13ad/200",
                items = listOf(
                    Item(
                        id = "b27e0c9c-7cab-4281-a80f-2fa01a34caef",
                        name = "Coca-Cola (12 pack)",
                        description = "12 pack of 12 oz cans of Coca-Cola.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/7c2cfefc-acfb-4f29-b40d-0dfd2c93e0a0/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7553d0f6-53e8-4fc7-8c31-0688a20d2e78",
                        name = "Bottled Water (24 pack)",
                        description = "24 pack of 16.9 oz bottles of purified water.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/1a49bc11-63ca-49a2-bf23-84577a1fb342/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "ff41df6e-b8e2-4ed0-91f8-c035e54ac37a",
                        name = "Orange Juice (64 oz)",
                        description = "64 oz bottle of 100% orange juice.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/b59e571b-0bff-4451-921d-d80c96aa7d3a/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "09eafdc3-a922-43fc-8599-4c3f274bea59",
                        name = "Ground Coffee (12 oz)",
                        description = "12 oz bag of medium roast ground coffee.",
                        price = 799, // $7.99
                        imageUrl = "https://picsum.photos/seed/a1ca1067-6fb0-4ee7-9216-e74a2438bef4/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "b8067e5d-df31-4e57-a657-87b78f113a3a",
                name = "Snacks",
                description = "Chips, cookies, and other snacks",
                imageUrl = "https://picsum.photos/seed/d4a599b0-fd42-46b1-b236-46d8b38988e6/200",
                items = listOf(
                    Item(
                        id = "b6244a27-13e1-4295-b44b-d1036bc53679",
                        name = "Potato Chips (8 oz)",
                        description = "8 oz bag of classic potato chips.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/904bec6a-7ecb-4d65-96b7-97882a801c3b/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "29cd2630-7fa1-4767-ad9c-84b6c7d5f244",
                        name = "Chocolate Chip Cookies (13 oz)",
                        description = "13 oz package of chocolate chip cookies.",
                        price = 349, // $3.49
                        imageUrl = "https://picsum.photos/seed/69b3f474-3cef-4fef-a8e3-c76d601626f6/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7f578a2b-1444-4312-a02d-9e0ca8a94004",
                        name = "Microwave Popcorn (6 pack)",
                        description = "6 pack of microwave popcorn bags.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/e1494c21-cfaa-412d-8a53-37a7fb3efe71/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            )
        ),
    )

    val wholeFoods = Merchant(
        id = "c930825e-2557-42df-9cdc-5f4295fa4973",
        name = "Whole Foods Market",
        type = Merchant.Type.Supermarket,
        minimumOrder = 2500, // $25.00
        isOpen = true,
        workingHours = listOf(
            // 7 AM to 10 PM
            WorkingHours(0, 420, 1320), // Sunday
            WorkingHours(1, 420, 1320), // Monday
            WorkingHours(2, 420, 1320), // Tuesday
            WorkingHours(3, 420, 1320), // Wednesday
            WorkingHours(4, 420, 1320), // Thursday
            WorkingHours(5, 420, 1320), // Friday
            WorkingHours(6, 420, 1320), // Saturday
        ),
        deliveryTime = 45, // 45 minutes
        deliveryFee = 399, // $3.99
        rating = 45, // 4.5/5.0
        ratingCount = 6432,
        imageUrl = "https://picsum.photos/seed/3bf8b937-fa2f-49b3-8a02-25f2564cfb7a/200",
        address = Address(
            id = "b21e502d-e063-41ca-ae12-d0ecedb10361",
            name = "Whole Foods Market SOMA",
            latitude = 37.7785,
            longitude = -122.3936,
            addressLine1 = "399 4th St",
            addressLine2 = "",
            city = "San Francisco",
            state = "CA",
            zip = "94107"
        ),
        phoneNumber = "(415) 618-0066",
        category = "Organic Supermarket",
        menu = listOf(
            Merchant.MenuCategory(
                id = "9a2d37b6-5f04-437a-a477-120366db4ddf",
                name = "Organic Produce",
                description = "Fresh organic fruits and vegetables",
                imageUrl = "https://picsum.photos/seed/3b10a06b-44b9-4860-a488-18b461f636e7/200",
                items = listOf(
                    Item(
                        id = "825968eb-b834-4a5c-abdb-3c9d191af9a9",
                        name = "Organic Bananas (bunch)",
                        description = "Fresh organic bananas, approximately 5-7 per bunch.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/5584964e-540b-42cf-ba05-c8c1764c387b/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7bea04f4-7b8b-41a7-a6e5-2afa0a36bd05",
                        name = "Organic Honeycrisp Apples (1 lb)",
                        description = "Fresh organic Honeycrisp apples, sold by the pound.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/84899ebd-0af6-4f6e-b8a3-f16a4d4de2a9/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "a59b9a65-1442-442e-8ff2-f918924120db",
                        name = "Organic Hass Avocados (each)",
                        description = "Ripe organic Hass avocados, sold individually.",
                        price = 249, // $2.49
                        imageUrl = "https://picsum.photos/seed/06658e76-fb84-4f37-881d-f52508815cb7/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "66d446e9-8a77-4015-86f9-697fdd679d37",
                        name = "Organic Kale (bunch)",
                        description = "Fresh organic kale, sold by the bunch.",
                        price = 299, // $2.99
                        imageUrl = "https://picsum.photos/seed/8795f078-14c2-4797-94c3-867593ee4480/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "a0607b2a-3933-4404-8303-1df8cdc474ed",
                        name = "Organic Blueberries (6 oz)",
                        description = "Fresh organic blueberries, 6 oz package.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/afc18366-92ce-4a5d-8af2-4e6868bccc2a/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "4b941a52-eaaf-4217-830c-9bce2b9ab988",
                name = "Dairy & Alternatives",
                description = "Organic dairy products and plant-based alternatives",
                imageUrl = "https://picsum.photos/seed/d3ddfa09-29b0-444e-975e-907d37dd2156/200",
                items = listOf(
                    Item(
                        id = "4d68274f-1339-457b-97de-c7c36aacbbb2",
                        name = "Organic Whole Milk (half gallon)",
                        description = "Half gallon of organic whole milk.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/febf911f-1ebe-4093-8859-99874da992e8/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "eff5be3d-a597-4b8b-a81e-8534a0bd02e6",
                        name = "Unsweetened Almond Milk (64 oz)",
                        description = "64 oz carton of unsweetened almond milk.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/6ae426bf-e87f-48ff-83d7-ce333d6dde48/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "0657deb5-cd9e-4081-b9bf-01e9d02ea2e4",
                        name = "Organic Free-Range Eggs (dozen)",
                        description = "One dozen organic free-range eggs.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/ef192f8c-4b90-4ea3-96d3-faa46e378435/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7aac9df1-b42c-48c4-9ce9-7baef3008746",
                        name = "Organic Sharp Cheddar (8 oz)",
                        description = "8 oz block of organic sharp cheddar cheese.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/3b893ba5-ede4-4161-90df-53bc58763ee4/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "0cac3739-24bb-4ba6-a5a8-c247ed42557d",
                name = "Sustainable Meat & Seafood",
                description = "Organic and sustainably sourced meat and seafood",
                imageUrl = "https://picsum.photos/seed/9fcd9147-2908-41a9-b1b6-9060072acb2a/200",
                items = listOf(
                    Item(
                        id = "a7fbf9e4-2252-451c-bb2a-3a11b1bbac29",
                        name = "Organic Chicken Breast (1 lb)",
                        description = "1 pound of organic, free-range chicken breast.",
                        price = 899, // $8.99
                        imageUrl = "https://picsum.photos/seed/ea5b500a-c457-44f0-aac8-181f4e639f85/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "5858401c-a3a9-41ed-9a95-e173cb60b016",
                        name = "Grass-Fed Ground Beef (1 lb)",
                        description = "1 pound of 85/15 grass-fed ground beef.",
                        price = 899, // $8.99
                        imageUrl = "https://picsum.photos/seed/5ca8f692-356f-4a4f-8a29-06c1c1807d4c/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "8022d827-c6a8-4c87-9975-74b6379d9171",
                        name = "Wild-Caught Salmon Fillet (1 lb)",
                        description = "1 pound of wild-caught Alaskan salmon fillet.",
                        price = 1499, // $14.99
                        imageUrl = "https://picsum.photos/seed/27b27c7a-23ca-4370-a047-2237d9a9ffa8/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "aaaa5e3e-4a08-450c-950f-6c52580bde44",
                        name = "Organic Extra-Firm Tofu (14 oz)",
                        description = "14 oz package of organic extra-firm tofu.",
                        price = 349, // $3.49
                        imageUrl = "https://picsum.photos/seed/7975feb0-3d1b-4715-9d66-f1c035a423bf/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "b270eec2-e592-48a5-af69-0fa12a2c6543",
                name = "Artisan Bakery",
                description = "Fresh-baked organic breads and pastries",
                imageUrl = "https://picsum.photos/seed/24bc1572-9a0d-4ecf-84f3-725ff17c6a5f/200",
                items = listOf(
                    Item(
                        id = "30ab675a-08c1-4f09-a368-32b47578be83",
                        name = "Artisan Sourdough Bread (16 oz)",
                        description = "16 oz loaf of freshly baked sourdough bread.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/a90e5b66-00d6-430f-afde-8605012dfc6b/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "d9c70cad-abd5-4d8e-8429-83f0332290a2",
                        name = "Gluten-Free Sandwich Bread (18 oz)",
                        description = "18 oz loaf of gluten-free sandwich bread.",
                        price = 699, // $6.99
                        imageUrl = "https://picsum.photos/seed/f12e977c-a714-4fd6-84b0-e13142ecec5c/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7f140db4-1221-4919-b8ac-1b48e4ed8085",
                        name = "Vegan Blueberry Muffins (4 count)",
                        description = "4 count package of vegan blueberry muffins.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/664c99cb-7c3c-4516-bae6-c7935bc7412a/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "a6691443-1499-4851-a6e4-ee23410bc002",
                name = "Natural Pantry",
                description = "Organic and natural pantry staples",
                imageUrl = "https://picsum.photos/seed/326a2974-3cc7-448a-8bf6-363324e2d3d8/200",
                items = listOf(
                    Item(
                        id = "158f372c-ef19-4562-9c3e-a6635f57f1a0",
                        name = "Organic Quinoa (16 oz)",
                        description = "16 oz package of organic quinoa.",
                        price = 599, // $5.99
                        imageUrl = "https://picsum.photos/seed/69dd8e96-564b-4073-9d1e-bbd7f2a43132/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "66484ece-853a-46e5-8c4a-c7f11b72d03e",
                        name = "Organic Extra Virgin Olive Oil (16.9 oz)",
                        description = "16.9 oz bottle of organic extra virgin olive oil.",
                        price = 1299, // $12.99
                        imageUrl = "https://picsum.photos/seed/999c4ab7-ca2f-4853-bb81-a8ea4f8aab20/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "7919e329-563c-4e43-a3a8-eae8a0fd39f2",
                        name = "Organic Raw Honey (16 oz)",
                        description = "16 oz jar of organic raw honey.",
                        price = 899, // $8.99
                        imageUrl = "https://picsum.photos/seed/b5b30d8e-1453-4418-be67-bb53270a8b43/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "0dc54407-7fe2-492d-b46d-b96e2d7c931a",
                        name = "Organic Whole Wheat Pasta (16 oz)",
                        description = "16 oz package of organic whole wheat pasta.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/b5ee8330-c955-473c-a377-dab8fcdee41d/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "9e1a78e0-9882-4c7a-9254-1a5bb06cecc2",
                        name = "Organic Virgin Coconut Oil (14 oz)",
                        description = "14 oz jar of organic virgin coconut oil.",
                        price = 799, // $7.99
                        imageUrl = "https://picsum.photos/seed/ffd1dbf7-a665-4f62-8f65-8e3e9e151f00/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "5918231d-dcca-4be7-a826-8c8460ae2ae8",
                name = "Healthy Snacks",
                description = "Organic and natural snacks",
                imageUrl = "https://picsum.photos/seed/c0d77e5d-6d2d-47df-8790-6443a1eea7a5/200",
                items = listOf(
                    Item(
                        id = "65320169-db41-48cd-9162-5ee693599b82",
                        name = "Organic Trail Mix (12 oz)",
                        description = "12 oz bag of organic trail mix with nuts and dried fruits.",
                        price = 799, // $7.99
                        imageUrl = "https://picsum.photos/seed/f8541e69-9658-42d3-ace3-90904ccf6ea4/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "ed324ae7-17ba-4ec8-a7b7-4124c285339a",
                        name = "Organic Kale Chips (2 oz)",
                        description = "2 oz bag of organic kale chips.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/ed76e4a5-4900-417f-8198-5da1981c2e10/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "19916662-80ad-4c9f-805b-1d3572943144",
                        name = "Organic Dark Chocolate Bar (3 oz)",
                        description = "3 oz bar of 70% organic dark chocolate.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/2d45e9c8-4b9e-4361-b422-eb6c204591ab/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "cf160bc8-dd58-44e6-b65c-923fb06e68a5",
                        name = "Organic Hummus (8 oz)",
                        description = "8 oz container of organic hummus.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/274e030c-a074-4ddf-b41d-a3ab5bfaf6f5/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            ),
            Merchant.MenuCategory(
                id = "b7a8692f-acaf-4b29-809b-65386f89684e",
                name = "Natural Beverages",
                description = "Organic and natural beverages",
                imageUrl = "https://picsum.photos/seed/30adb58c-f085-4dc7-96be-108ee253f912/200",
                items = listOf(
                    Item(
                        id = "8d7d846d-8101-4485-9d01-6575b98dd885",
                        name = "Organic Kombucha (16 oz)",
                        description = "16 oz bottle of organic kombucha.",
                        price = 399, // $3.99
                        imageUrl = "https://picsum.photos/seed/e21d57f0-a57d-45ba-bf9d-402f8550e30f/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "56507716-e65c-4c3d-b76d-22fb9d636631",
                        name = "Organic Coconut Water (16.9 oz)",
                        description = "16.9 oz bottle of organic coconut water.",
                        price = 349, // $3.49
                        imageUrl = "https://picsum.photos/seed/42c7f9e2-95bf-42c5-96ee-4e887f42d9ff/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "919f6bf1-2812-4ee7-be37-0c3fbf438132",
                        name = "Organic Fair Trade Coffee (12 oz)",
                        description = "12 oz bag of organic fair trade coffee beans.",
                        price = 1299, // $12.99
                        imageUrl = "https://picsum.photos/seed/ed9336d7-c6d5-4439-bb39-c11b6f6e8575/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "33ae686b-12a3-458a-912d-87f20ac6db59",
                        name = "Organic Green Tea (16 bags)",
                        description = "16 bags of organic green tea.",
                        price = 499, // $4.99
                        imageUrl = "https://picsum.photos/seed/128ea811-fd85-4fd2-b352-d00542094049/200",
                        options = listOf(),
                        isAvailable = true
                    ),
                    Item(
                        id = "70bae052-1eb1-4ecc-bcfc-8569612385c4",
                        name = "Cold-Pressed Green Juice (12 oz)",
                        description = "12 oz bottle of cold-pressed green juice.",
                        price = 699, // $6.99
                        imageUrl = "https://picsum.photos/seed/e566c614-ce31-461a-9e5b-1e5c40211280/200",
                        options = listOf(),
                        isAvailable = true
                    )
                )
            )
        ),
    )
}
