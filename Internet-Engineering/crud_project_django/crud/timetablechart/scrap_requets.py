import requests
from requests.adapters import HTTPAdapter
from requests.packages.urllib3.util.retry import Retry 
import re
from bs4 import BeautifulSoup
import gc

import logging
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger()

class MyError(ValueError):
    def __init__(self, err_message=None, err_code=None):
        self.args = (err_message, err_code)


def main(un, pw, debtor=False):
    
    try:
        retry_strategy = Retry(
            total=3,
            status_forcelist=[429, 500, 502, 503, 504],
            method_whitelist=["HEAD", "GET", "PUT", "DELETE", "OPTIONS", "TRACE", "POST"],
            backoff_factor=1,
        )
        adapter = HTTPAdapter(max_retries=retry_strategy)
        http = requests.Session()
        http.mount("https://", adapter)
        http.mount("http://", adapter)

        data = {
            'Command': 'LOGIN',
            'username': un,
            'password': pw,
            #'SSMUsername_txt': user_data['username'],
            #'SSMPassword_txt': user_data['password'],
        }

        login_request = http.post('http://sada.guilan.ac.ir/SubSystem/Edari/PRelate/Site/SignIn.aspx', data=data, timeout=7)
        dashboard_param_search = re.search(r'\(\"http\:\/\/sada\.guilan\.ac\.ir\/Dashboard\.aspx\?param\=(?P<param>.*?)\"\)', login_request.text)

        if dashboard_param_search is None:
            if login_request.text.find('رمز عبور شما اشتباه ميباشد') >= 0 or login_request.text.find('نام کاربري يا کلمه عبور شما اشتباه ميباشد') >= 0:
                raise MyError('incorrect password_or_username', 'iup')  # incorrect username password
            else:
                raise Exception('dashbord_param or incorrect_password_or_username_message not found', 'dpnf')  # dashbord param not found

        dashboard_param = dashboard_param_search.group('param')

        if debtor:
            workbook_request = http.post('http://sada.guilan.ac.ir/Dashboard.aspx', params={'param': dashboard_param}, data={'Command': 'GET_TAB_INFO:020205'}, timeout=7)
            workbook_param_search = re.search(r'\/Subsystem\/Amozesh\/Stu\/WorkBook\/StdWorkBook_Index\.aspx\?param\=(?P<param>.*)', workbook_request.text)
            if workbook_param_search is None:
                raise Exception('workbook_param not found', 'wpnf')  # workbook param not found
            workbook_param = workbook_param_search.group('param')
            request_for_last_term = http.get('http://sada.guilan.ac.ir/Subsystem/Amozesh/Stu/WorkBook/StdWorkBook_Index.aspx', params={'param': workbook_param}, timeout=15)
            last_term = BeautifulSoup(request_for_last_term.text, 'lxml')
            last_term = last_term.find(id='Term_Drp')
            last_term = last_term.find_all('option')[-1]['value']

            data={'SubIs_Chk':'false', 'Command':'Log:Vahed', 'Hitab':'Vahed', 'TypeCard_Drp':'rpGrade_Karname_2', 'mx_grid_info':'0;1;1;1;;;onGridLoad;1;;', 'Term_Drp':last_term}
            last_term_page = http.post('http://sada.guilan.ac.ir/Subsystem/Amozesh/Stu/WorkBook/StdWorkBook_Index.aspx', params={'param': workbook_param}, data=data, timeout=10)
            soup = BeautifulSoup(last_term_page.text, 'lxml')

            tables = soup.find_all('table')
            del soup, data, login_request, dashboard_param, dashboard_param_search, workbook_request, workbook_param_search, workbook_param, request_for_last_term, last_term_page

            time_column_index = 5
            for column_index, column in enumerate(tables[0].find_all('th')):
                if column.text == 'برنامه زماني':
                    time_column_index = column_index
            table = tables[-1]
            rows = table.find_all('tr')
            data = []
            for row_index, row in enumerate(rows):
                parts_of_row = row.find_all('td')

                ostad = parts_of_row[time_column_index - 1].text.replace('\n ', '').replace('\n', '')
                dars = parts_of_row[1].text
                lines = parts_of_row[time_column_index].text
                lines = list(filter(lambda x: x != '', lines.split('**')))
                p = r'(?P<first_comment>.*?)\s*(?P<day>چهارشنبه|سه شنبه|دوشنبه|يکشنبه|پنج شنبه|شنبه)\s*(?P<second_comment>.*?)\s*(?P<start>\d{2}\:\d{2})\s+\-\s+(?P<end>\d{2}\:\d{2})\s*(?P<last_comment>.*)'
                for line in lines:
                    res = re.search(p, line)
                    
                    data.append([
                        res.group('start'),
                        res.group('end'),
                        res.group('day'),
                        dars,
                        ostad,
                        ' '.join([res.group('first_comment'), res.group('second_comment'), res.group('last_comment')]),
                    ])

            
            gc.collect()
            return data

        else:
            report_request = http.post('http://sada.guilan.ac.ir/Dashboard.aspx', params={'param': dashboard_param}, data={'Command': 'GET_TAB_INFO:020203'}, timeout=7)

            report_param_search = re.search(r'\/Subsystem\/Amozesh\/Sabtenam\/Tasbir\/Report\/Report\.aspx\?param\=(?P<param>.*)', report_request.text)
            if report_param_search is None:
                if report_request.text.find('بدهکار') >= 0:
                    return main(un, pw, True)
                    # raise MyError('report problem because of debt', 'd')  # debt
                else:
                    raise Exception('report_param or debt_message not found', 'rpnf')  # report param not found
            report_param = report_param_search.group('param')

            report_page = http.get('http://sada.guilan.ac.ir/Subsystem/Amozesh/Sabtenam/Tasbir/Report/Report.aspx', params={'param': report_param}, timeout=7)

            soup = BeautifulSoup(report_page.text, 'lxml')
            rows = soup.find_all('table', class_='grd')
            del soup, data, login_request, dashboard_param, dashboard_param_search, report_page, report_param_search, report_param, report_request

            for column_index in range(len(rows[0].find_all('td'))):
                if rows[0].find_all('td')[column_index].find('span').text == 'زمان برگزاري':
                    time_column_index = column_index
            rows = rows[1:]
            number_of_rows = 0
            for row_index in range(len(rows)):
                try:
                    int(rows[row_index].find_all('td')[0].find('span').text)
                    number_of_rows = row_index

                except ValueError:
                    break
            gc.collect()
            rows = rows[0:number_of_rows + 1]
            data = []
            for row in rows:
                parts_of_row = row.find_all('td')
                
                ostad = parts_of_row[time_column_index - 1].find('span').text.replace('\n ', '').replace('\n', '')
                dars = parts_of_row[2].find('span').text
                lines = parts_of_row[time_column_index].find('span').text
                lines = list(filter(lambda x: x != '', lines.split('**')))
                p = r'(?P<first_comment>.*?)\s*(?P<day>چهارشنبه|سه شنبه|دوشنبه|يکشنبه|پنج شنبه|شنبه)\s*(?P<second_comment>.*?)\s*(?P<start>\d{2}\:\d{2})\s+\-\s+(?P<end>\d{2}\:\d{2})\s*(?P<last_comment>.*)'
                
                for line in lines:
                    res = re.search(p, line)
                    
                    data.append([
                        res.group('start'),
                        res.group('end'),
                        res.group('day'),
                        dars,
                        ostad,
                        ' '.join([res.group('first_comment'), res.group('second_comment'), res.group('last_comment')]),
                    ])

            gc.collect()
            return data
        
    except MyError as e:
        logger.info(str(un + '  ||  ' + pw))
        logger.warning(str(e.args))
        
    except Exception as e:
        logger.info(str(un + '  ||  ' + pw))
        logger.warning(str(e.args))
        