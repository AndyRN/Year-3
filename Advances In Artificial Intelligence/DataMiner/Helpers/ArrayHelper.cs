using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataMiner.Helpers
{
    public static class ArrayHelper
    {

        public static T[] GetRange<T>(this T[] data, int index, int length)
        {
            T[] result = new T[length];

            Array.Copy(data, index, result, 0, length);

            return result;
        }

        public static T[] Append<T>(this T[] array, T[] target)
        {
            T[] appendedArray = new T[array.Length + target.Length];

            array.CopyTo(appendedArray, 0);
            target.CopyTo(appendedArray, array.Length);

            return appendedArray;
        }
    }
}
