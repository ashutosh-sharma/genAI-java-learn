## Task 1 - _Generative AI basics_

### How to run the project:
- Configure your `API_CLIENT_KEY` & `CLIENT_ENDPOINT` as VM arguments
- Run the spring boot application - `GenAiTrainingApplication`
- You can use tools like `Postman` to fire REST API calls to use the app

---
### How to provide request:
Pass input as request parameter: 
e.g.
`?input=I want to make breakdast today, suggest top 3 indian breakfast`


---
### Request examples:
### 1. 
- _REQUEST URL:_ `localhost:8080/api/chat?input=I want to make breakdast today, suggest top 3 indian breakfast in bullets`
- _RESPONSE_:  
``` 
{
    "input": "I want to make breakfast today, suggest top 3 indian breakfast in bullets",              
    "response": "Here are three delicious and popular Indian breakfast options for you to try:\n\n- **Poha**: A light and flavorful dish made from flattened rice, tossed with onions, green chilies, peanuts, turmeric, and garnished with fresh coriander and a squeeze of lime.\n\n- **Masala Dosa with Coconut Chutney**: A crispy, fermented rice and lentil crepe filled with a spiced potato mixture, served with coconut chutney and sambar on the side.\n\n- **Upma**: A savory semolina (sooji) dish cooked with vegetables, aromatic spices, mustard seeds, curry leaves, and garnished with fresh coriander.\n\nLet me know which one you pick, and I can help with the recipe! 😊"
}
```

### 2.
- _REQUEST URL:_ `localhost:8080/api/chat?input=I want to travel india, suggest top 2 places to visit with very short description`
- _RESPONSE_:  
``` 
{
    "input": "I want to travel india, suggest top 2 places to visit with very short description",
    "response": "1. **Jaipur, Rajasthan**: Known as the \"Pink City,\" Jaipur offers majestic palaces like the City Palace and Amber Fort, vibrant bazaars, and rich Rajasthani culture.  \n\n2. **Varanasi, Uttar Pradesh**: One of the world's oldest cities, Varanasi is a spiritual hub along the Ganges River, where you can witness mesmerizing Ganga Aarti and explore ancient temples."
}
```
