import requests
import csv
from bs4 import BeautifulSoup
from multiprocessing import Pool, Manager
import time
import pandas as pd

file = open('./data/kospi200.csv', 'r')
reader = csv.reader(file)


def get_page(code):
    url = 'https://www.google.com/finance/quote/' + code + ':KRX'
    result = requests.get(url)
    bs_obj = BeautifulSoup(result.content, "html.parser")
    return bs_obj.find(class_="YMlKec fxKbKc").string.removesuffix('.00').replace(',', '')[1:]


def get_price(company_code_list, result_list, index):
    price = get_page(company_code_list[index])
    result_list.append([company_code_list[index], price])


if __name__ == '__main__':
    m = Manager()
    company_code_list = result_list = []
    result_list = m.list()

    # company_code_list = ['종목코드', '035420', '096770', '051900']
    for line in reader:
        company_code_list.append(line[0])

    # start = time.time()
    pool = Pool(processes=4)

    pool.starmap(get_price, [(company_code_list, result_list, index) for index in range(1, len(company_code_list))])

    df = pd.DataFrame(list(result_list), columns=['company', 'price'])
    df.to_json('./data/price_now_{}.json'.format(time.localtime()), orient='records')

    # print("timelap : ", time.time() - start)
