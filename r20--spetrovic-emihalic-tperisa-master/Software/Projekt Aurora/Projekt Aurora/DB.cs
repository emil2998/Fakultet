using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Data;
using System.Windows.Forms;

namespace Projekt_Aurora
{
    public class DB
    {
        private static DB instance;

        public static DB Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new DB();
                }

                return instance;
            }
        }

        public string ConnectionString { get; private set; }

        public SqlConnection Connection { get; private set; }

        private DB()
        {
            ConnectionString = @"Data Source=31.147.204.119\PISERVER,1433; Initial Catalog=PI20_041_DB; User=PI20_041_User; Password=kH^F%]zJ";
            Connection = new SqlConnection(ConnectionString);
            Connection.Open();
        }

        public void CloseConnection()
        {
            if (Connection.State != System.Data.ConnectionState.Closed)
            {
                Connection.Close();
            }
        }

        public SqlDataReader DohvatiDataReader(string sqlUpit)
        {
            SqlCommand command = new SqlCommand(sqlUpit, Connection);
            return command.ExecuteReader();
        }

        public object DohvatiVrijednost(string sqlUpit)
        {
            SqlCommand command = new SqlCommand(sqlUpit, Connection);
            return command.ExecuteScalar();
        }

        public int IzvrsiUpit(string sqlUpit)
        {
            SqlCommand command = new SqlCommand(sqlUpit, Connection); 
            return command.ExecuteNonQuery();
        }
       
    }
}
