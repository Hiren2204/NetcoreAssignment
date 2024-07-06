// package main

// import (
// 	"fmt"
// 	"go-postgres/router"
// 	"log"
// 	"net/http"
// )

// func main() {

// 	r := router.Router()
// 	fmt.Println("Starting server on  8080 ...")
// 	log.Fatal(http.ListenAndServe(":8080", r))

// }

package main

import (
	"context"
	"database/sql"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
	_ "github.com/lib/pq"
	"github.com/redis/go-redis/v9"
	_ "github.com/redis/go-redis/v9"
)

type Stock struct {
	Name    string `json:"name"`
	Price   int64  `json:"price"`
	Company string `json:"company"`
	StockId int64  `json:"id"`
}

type redI struct {
	Name  string `json:"name"`
	Price int    `json:"price"`
	Id    string `json:"id"`
}

func main() {

	r := Router()
	fmt.Println("Starting server on  8080 ...")
	log.Fatal(http.ListenAndServe(":8080", r))

}

func Router() *mux.Router {
	router := mux.NewRouter()
	router.HandleFunc("/api/stock/{id}", getStock).Methods("GET", "OPTIONS")
	router.HandleFunc("/api/stock/", postStock).Methods("POST", "OPTIONS")

	router.HandleFunc("/api/redis/", postRed).Methods("POST", "OPTIONS")
	router.HandleFunc("/api/redis/{id}", getRed).Methods("GET", "OPTIONS")
	return router

}
func getRed(w http.ResponseWriter, r *http.Request) {
	ctx := context.Background()
	red := redis.NewClient(&redis.Options{

		Addr:     "localhost:6379",
		Password: "",
		DB:       0,
	})
	err := red.Ping(ctx).Err()
	if err != nil {
		fmt.Print(err)
	} else {
		fmt.Print("sucess")
	}
	param := mux.Vars(r)
	fmt.Print(param["id"])
	dta, err := red.HGetAll(ctx, param["id"]).Result()
	if err != nil {
		fmt.Print(err)
	}

	json.NewEncoder(w).Encode(dta)

}
func postRed(w http.ResponseWriter, r *http.Request) {
	ctx := context.Background()
	red := redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "",
		DB:       0,
	})
	err := red.Ping(ctx).Err()
	if err != nil {
		fmt.Print(err)
	} else {
		fmt.Print("sucess")
	}
	// fmt.Printf("type %T ", param["id"])

	var obj redI
	err = json.NewDecoder(r.Body).Decode(&obj)

	if err != nil {
		fmt.Print("decoding err ", err)
	}
	fmt.Println("id :", obj.Id)
	err = red.HSet(ctx, obj.Id, map[string]interface{}{
		"Name":  obj.Name,
		"price": obj.Price,
	}).Err()

	if err != nil {
		fmt.Print(err)
	} else {
		fmt.Print("successfully entered the map")
	}

}

func getStock(w http.ResponseWriter, r *http.Request) {

	db, err := sql.Open("postgres", "postgres://hirengohil:1234@localhost:5432/stocks?sslmode=disable")
	if err != nil {
		fmt.Print(err)
	}
	param := mux.Vars(r)
	var st Stock
	//reading parameter from url
	id, err := strconv.Atoi(param["id"])
	if err != nil {
		fmt.Print(err)
	}
	row := db.QueryRow("select * from stocks where id=$1", id)

	err = row.Scan(&st.Name, &st.Price, &st.Company, &st.StockId)

	if err != nil {
		fmt.Print(err)
	}
	json.NewEncoder(w).Encode(st)
}

func postStock(w http.ResponseWriter, r *http.Request) {
	fmt.Print("cllled post")
	db, err := sql.Open("postgres", "postgres://hirengohil:1234@localhost:5432/stocks?sslmode=disable")
	if err != nil {
		fmt.Print(err)
	}

	var stock Stock

	err = json.NewDecoder(r.Body).Decode(&stock)
	if err != nil {
		fmt.Print(err)
	}
	_, err = db.Query("insert into stocks(name,price,company,id) values($1,$2,$3,$4)", stock.Name, stock.Price, stock.Company, stock.StockId)
	if err != nil {
		fmt.Print("Cant inserted", err)
	} else {
		fmt.Print("inserted 1 row")
	}

}

//notes:
//1.to use struct with json name should start with Capital
