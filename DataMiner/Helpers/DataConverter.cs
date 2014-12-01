using System;
using System.Collections.Generic;

namespace DataMiner.Helpers
{
    public static class DataConverter
    {
        public static int[] Convert(string dataLine)
        {
            int[] dataBits = new int[Config.RuleLength];

            int i = 0;
            foreach (char bit in dataLine)
            {
                switch (bit)
                {
                    case '0':
                    case '1':
                        dataBits[i] = (int)Char.GetNumericValue(bit);
                        i++;
                        break;
                    default:
                        break;
                }
            }

            return dataBits;
        }
    }
}
