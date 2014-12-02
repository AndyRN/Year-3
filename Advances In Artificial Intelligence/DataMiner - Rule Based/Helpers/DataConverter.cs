using System;
using System.Collections.Generic;

namespace DataMiner.Helpers
{
    public static class DataConverter
    {
        public static double[] Convert(string dataLine)
        {
            double[] dataBits = Array.ConvertAll(dataLine.Split(' '), Double.Parse);

            return dataBits;
        }
    }
}
