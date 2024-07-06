package main

import (
	"fmt"
	"log"
	"runtime"
	"sync"
	"time"

	"github.com/jmoiron/sqlx"
	_ "github.com/lib/pq"
)

const (
	host     = "localhost"
	port     = 5432
	user     = "hirengohil"
	password = "1234"
	dbname   = "stocks"
)

type User struct {
	Name string `db:"name"`
	ID   int    `db:"id"`
}

var list []User

func main() {
	runtime.GOMAXPROCS(runtime.NumCPU())
	connStr := fmt.Sprintf("host=%s port=%d user=%v password=%v dbname=%v sslmode=disable", host, port, user, password, dbname)
	db, err := sqlx.Open("postgres", connStr)
	if err != nil {
		log.Fatal("Error opening database: ", err)
	}
	defer db.Close()

	err = db.Ping()
	if err != nil {
		log.Fatal("Error connecting to the database: ", err)
	}
	fmt.Println("Connected to the database")
	st := time.Now()

	totalRow := 10000000
	bathc := 1000
	for i := 0; i < totalRow; i++ {
		list = append(list, User{
			Name: fmt.Sprint("av-", i),
			ID:   i,
		})
	}
	sqls := `INSERT INTO Us(name, id) VALUES (:name, :id)`
	var wg sync.WaitGroup
	for i := 0; i < totalRow; i += bathc {
		wg.Add(1)
		end := i + bathc
		if end > totalRow {
			end = totalRow
		}
		go process(i, end, &wg, db, sqls)
	}

	wg.Wait()

	elp := time.Since(st)
	fmt.Print(elp)

}

func process(i int, j int, waitGroup *sync.WaitGroup, db *sqlx.DB, sqls string) {
	defer waitGroup.Done()
	user := list[i:j]
	_, err := db.NamedExec(sqls, user)
	if err != nil {
		fmt.Print(err)
	}

}
