# Task 4 - Semantic Kernel Plugins

### Description 
Added various semantic kernel plugins - in general, any plugin that can do calculations or provide information outside of model knowledge.

### Samples

#### 1.
`Prompt - Get the current date`

<img width="608" alt="image" src="https://github.com/user-attachments/assets/050e039b-e2df-445d-8c76-5c6d9573b046" />
The system has identified that `CurrentDateTimeCalculatorPlugin` plugin should be used to cater to the given prompt, and generated a relevant result.

---
#### 2. 
`Prompt - Get the current time`
<img width="614" alt="image" src="https://github.com/user-attachments/assets/b71b0a22-de7b-40a7-be03-e4e6d7c74230" />
The system has identified that `CurrentDateTimeCalculatorPlugin` plugin should be used to cater to the given prompt and has generated the relevant result.

---
#### 3.
`Prompt - Calculate age based on the given date of birth 1997-01-30`
<img width="646" alt="image" src="https://github.com/user-attachments/assets/3e4e8718-140c-4f1d-ac71-187323079d71" />
The system has identified that `AgeCalculatorPlugin` plugin should be used to cater to the given prompt and has generated the relevant result.



-------------------------

## Task 3 - Working with different models


### `/Deployments` API - The API fetches all available Model names that can be passed on while prompting.

<img width="650" alt="deployment-API-response" src="https://github.com/user-attachments/assets/af99db3c-3afe-4c85-bdba-e6582103d6a9" />


---------------------
### Prompting with different models

I have provided a system prompt to help users interact with a store that has three items: Mobile, Television, and headphones, see below:
<img width="796" alt="image" src="https://github.com/user-attachments/assets/d12dc4f6-ef47-4454-9558-f47e1e74adf6" />

---

I tried the prompt, "List all items in the shop—tell me a poem about it," with different models. 


### See the responses below for various models and `temperature` values:


* Model: gpt-4o
* Temperature: 0.1
<img width="650" alt="gpt-4o" src="https://github.com/user-attachments/assets/26e0e408-aa6a-471b-9943-e81817595a98" />

-------------------
* Model: gpt-4o
* Temperature: 0.9
<img width="643" alt="gpt-4o-temp-9" src="https://github.com/user-attachments/assets/c3944935-375b-4b5f-b420-2d13380174b7" />

---------------------------
* Model: chat-bison
* Temperature: 0.1
<img width="644" alt="bison-response" src="https://github.com/user-attachments/assets/ca1adaa3-abd3-4636-af20-2d8f94cef30c" />

----------------------------
* Model: chat-bison
* Temperature: 0.9
<img width="643" alt="bison-temp-9" src="https://github.com/user-attachments/assets/111ec8ee-5d6f-459d-bf55-6a218a1e03e6" />

----------------------------
* Model: DeepSeek - DeepSeek-R1-Distill-Llama-70B-FP8
* Temperature: 0.1
<img width="634" alt="DeepSeek-response" src="https://github.com/user-attachments/assets/beaf0051-5b08-415f-8e65-570599e9eaab" />


----------------------------
----------------------------
----------------------------
----------------------------

# Task 5: Embeddings (Using Qdrant)
------------------
This needs to set up a Qdrant client to interact with the Qdrant vector database service running in a Local Docker container.
Qdrant? - High-Scale Vector Database

Qdrant dashboard URL - `http://localhost:6333/dashboard#/welcome`

`/embeddings/build` - Build Embedding from Text
<img width="632" alt="image" src="https://github.com/user-attachments/assets/e5b333dd-0eae-4f3f-96bc-45b909e06651" />


`/embeddings/build-and-store` - Build and store Embedding in Qdrant
<img width="632" alt="image" src="https://github.com/user-attachments/assets/dfc39881-6589-4637-aee0-7c330d39716e" />


`/embeddings/search` - Search for closest Embeddings

----
### Search for - Mug
<img width="615" alt="image" src="https://github.com/user-attachments/assets/e42a2e67-22e9-4e87-9d78-5391eeaa681e" />

### Search for - Cup
<img width="629" alt="image" src="https://github.com/user-attachments/assets/3d4a5391-5849-463c-b295-9a711109e541" />




