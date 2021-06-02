import requests
import csv
from bs4 import BeautifulSoup
from multiprocessing import Pool
import time
import pandas as pd


class Crawler:
    def __init__(self, process_num):
        self.result_list = []
        self.company_list = []
        self.pool = Pool(processes=process_num)
        self.data = None

    def set_company_list(self, user_list=None):
        if user_list is None:
            file = open('./data/kospi200.csv', 'r', encoding='UTF8')
            reader = csv.reader(file)

            for line in reader:
                self.company_list.append(line[0])
        else:
            self.company_list = user_list

    def job(self):
        self.result_list = self.pool.map(self.get_info, range(1, len(self.company_list)))

    def get_info(self, index):
        info = self.get_page(self.company_list[index])
        return [self.company_list[index], info[0], info[1]]

    def get_page(self, code):
        # url = 'https://www.google.com/finance/quote/' + code + ':KRX'
        url = 'https://finance.naver.com/item/main.nhn?code=' + code
        result = requests.get(url)
        bs_obj = BeautifulSoup(result.content, "lxml")
        # return bs_obj.find(class_="YMlKec fxKbKc").string.removesuffix('.00').replace(',', '')[1:]
        # return bs_obj.select_one('p.no_today').text.strip().split()[0].replace(',', '')
        a = bs_obj.select_one('h4.h_sub.sub_tit7 > em > a')
        if a is None:
            return ['null',
                    bs_obj.select_one('p.no_today').text.strip().split()[0].replace(',', '')]
        return [a.text,
                bs_obj.select_one('p.no_today').text.strip().split()[0].replace(',', '')]

    def end_job(self):
        self.pool.close()
        self.pool.join()

    def __getstate__(self):
        self_dict = self.__dict__.copy()
        del self_dict['pool']
        return self_dict

    def __setstate__(self, state):
        self.__dict__.update(state)


if __name__ == '__main__':
    start = time.time()

    kospi200 = Crawler(4)

    # ["073240", "161890", "003240", "001800", "005440", "009420", "014820", "020000", "020560"]
    kospi200.set_company_list()
    kospi200.job()
    kospi200.end_job()

    df = pd.DataFrame(list(kospi200.result_list), columns=['company', 'industry', 'price'])
    print(df)
    df.to_json('./data/price_now_{}.json'.format(time.strftime('%y-%m-%d-%H-%M-%S', time.localtime(time.time()))),
               orient='records', force_ascii=False)

    print("timelapse : ", time.time() - start)
