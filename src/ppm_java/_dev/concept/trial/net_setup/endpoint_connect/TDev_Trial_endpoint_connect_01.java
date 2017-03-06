/* -----------------------------------------------------------------------------
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
----------------------------------------------------------------------------- */

package ppm_java._dev.concept.trial.net_setup.endpoint_connect;

import ppm_java.backend.TController;
import ppm_java.util.logging.TLogger;

/**
 * Test: Connecting end points. We incorporate:
 * 
 * => Connecting compatible types
 * => Connecting soft incompatible endpoint types (e.g. OUTPUT to incompatible INPUT)
 * => Connecting hard incompatible endpoint types (e.g. OUTPUT to another OUTPUT)
 * 
 * Output:
 * 
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::cTor(): Creating controller (singleton)
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'pump'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (pump): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'dbconv_1'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (dbconv_1): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'dbconv_2'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (dbconv_2): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'dbconv_3'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (dbconv_3): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'dbconv_4'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (dbconv_4): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'db_1_in'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (db_1_in): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_In::cTor: Processor: 'dbconv_1': Creating input port 'db_1_in'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'db_1_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (db_1_out): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_Out::cTor: Processor: 'dbconv_1': Creating output port 'db_1_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'db_2_in'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (db_2_in): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_In::cTor: Processor: 'dbconv_2': Creating input port 'db_2_in'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'db_2_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (db_2_out): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_Out::cTor: Processor: 'dbconv_2': Creating output port 'db_2_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'db_3_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (db_3_out): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_Out::cTor: Processor: 'dbconv_3': Creating output port 'db_3_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'db_4_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (db_4_out): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_Out::cTor: Processor: 'dbconv_4': Creating output port 'db_4_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'pump_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (pump_out): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.pump.TNodePump_Endpoint_Out::cTor: Processor: 'pump': Creating output port 'pump_out'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: : ----------------------------------------------------------------------------------------------------
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_Out::SetTarget: Connecting endpoints: 'db_1_out' (VAudioPort_Output_Samples)  -> 'db_2_in' (VAudioPort_Input_Samples): OK
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'ppm_java.backend.TConnection_0'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (ppm_java.backend.TConnection_0): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: : _Test_Compatible(): Finished
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: : ----------------------------------------------------------------------------------------------------
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * WARNING: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.module.pump.TNodePump_Endpoint_Out::SetTarget: Connecting endpoints: 'pump_out' (VAudioPort_Output_Chunks_NoBuffer)  -> 'db_1_in' (VAudioPort_Input_Samples): Failure: Incompatible endpoint types
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TController::_Register (VBrowseable b): Registering object: 'ppm_java.backend.TConnection_1'
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: ppm_java.backend.TRegistry::Register (ppm_java.backend.TConnection_1): Object registered
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: : _Test_SoftIncompatible(): Finished
 * Mar 06, 2017 11:40:19 AM ppm_java.util.logging.TLogger _Log
 * INFO: Mon Mar 06 11:40:19 CET 2017: : ----------------------------------------------------------------------------------------------------
 * Exception in thread "main" java.lang.ClassCastException: ppm_java.backend.module.converter_db.TNodeConverterDb_Endpoint_Out cannot be cast to ppm_java.typelib.VAudioPort_Input
 *     at ppm_java.backend.TConnection.CreateInstance(TConnection.java:48)
 *     at ppm_java.backend.TController.Create_Connection_Data(TController.java:64)
 *     at ppm_java._dev.concept.trial.net_setup.endpoint_connect.TDev_Trial_endpoint_connect_01._Test_HardIncompatible(TDev_Trial_endpoint_connect_01.java:190)
 *     at ppm_java._dev.concept.trial.net_setup.endpoint_connect.TDev_Trial_endpoint_connect_01.main(TDev_Trial_endpoint_connect_01.java:126)
 */
public class TDev_Trial_endpoint_connect_01
{
    public static void main (String[] args)
    {
        TLogger.CreateInstance ();
        
        _Setup ();
        _Test_Compatible ();
        _Test_SoftIncompatible ();
        _Test_HardIncompatible ();
    }
    
    private static void _Setup ()
    {
        /* Create modules */
        TController.Create_Module_Pump              ("pump"                         );
        TController.Create_Module_ConverterDB       ("dbconv_1"                     );
        TController.Create_Module_ConverterDB       ("dbconv_2"                     );
        TController.Create_Module_ConverterDB       ("dbconv_3"                     );
        TController.Create_Module_ConverterDB       ("dbconv_4"                     );
        
        /* For each module, create in/out ports */
        TController.Create_Port_In                  ("dbconv_1",        "db_1_in"   );
        TController.Create_Port_Out                 ("dbconv_1",        "db_1_out"  );
        TController.Create_Port_In                  ("dbconv_2",        "db_2_in"   );
        TController.Create_Port_Out                 ("dbconv_2",        "db_2_out"  );
        TController.Create_Port_Out                 ("dbconv_3",        "db_3_out"  );
        TController.Create_Port_Out                 ("dbconv_4",        "db_4_out"  );
        TController.Create_Port_Out                 ("pump",            "pump_out"  );
        TLogger.LogMessage ("----------------------------------------------------------------------------------------------------");
    }
    
    /**
     * Connect compatible endpoints. Should just work without problems.
     */
    private static void _Test_Compatible ()
    {
        /* Connect ports. */
        TController.Create_Connection_Data ("db_1_out", "db_2_in");
        
        /* If we're still functional, we'll log this message. */
        TLogger.LogMessage ("_Test_Compatible(): Finished");
        TLogger.LogMessage ("----------------------------------------------------------------------------------------------------");
    }
    
    /** 
     * Connect modules. Ports are soft incompatible,
     * i.e. a child of VAudioPort_Output connected 
     *      to a child of VAudioPort_Input, but their children
     *      can't be connected:
     * pump:    VAudioPort_Output_Chunks_NoBuffer
     * dbconv:  VAudioPort_Input_Samples
     * 
     * Should throw a warning, but the program should continue 
     * to run after the warning.
     */
    private static void _Test_SoftIncompatible ()
    {
        /* Connect ports. */
        TController.Create_Connection_Data ("pump_out", "db_1_in");
        
        /* If we're still functional, we'll log this message. */
        TLogger.LogMessage ("_Test_SoftIncompatible(): Finished");
        TLogger.LogMessage ("----------------------------------------------------------------------------------------------------");
    }
    
    /**
     * Connect hard incompatible endpoints. Should throw an exception
     * and immediately terminate the program.
     */
    private static void _Test_HardIncompatible ()
    {
        /* Connect modules. Ports are compatible */
        TController.Create_Connection_Data ("db_3_out", "db_4_out");
        
        /* If we're still functional, we'll log this message. */
        TLogger.LogMessage ("_Test_HardIncompatible(): Finished");
        TLogger.LogMessage ("----------------------------------------------------------------------------------------------------");
    }
}
